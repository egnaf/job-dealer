package edu.netcracker.jobdealer.service.impl;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.exceptions.AccountByEmailNotFoundException;
import edu.netcracker.jobdealer.exceptions.AccountByIdNotFoundException;
import edu.netcracker.jobdealer.exceptions.AccountNotFoundException;
import edu.netcracker.jobdealer.exceptions.EmailExistsException;
import edu.netcracker.jobdealer.repository.AccountRepository;
import edu.netcracker.jobdealer.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private String salt = BCrypt.gensalt();

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getByEmail(String email) throws AccountByEmailNotFoundException {
        return accountRepository.findByEmail(email)
                .orElseThrow(() -> new AccountByEmailNotFoundException(email));
    }

    @Override
    public Account getById(UUID id) throws AccountByIdNotFoundException {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountByIdNotFoundException(id));
    }


    @Override
    public Account getUserByEmail(String email) throws AccountByEmailNotFoundException {
        return accountRepository.findByEmail(email).orElseThrow(() -> new AccountByEmailNotFoundException(email));
    }

    @Override
    public Account changePassword(String email, String newPass) throws AccountByEmailNotFoundException {
        if (accountRepository.existsByEmail(email)) {
            String pwd = BCrypt.hashpw(newPass, salt);
            Account user = getUserByEmail(email);
            user.setPassword(pwd);
            accountRepository.save(user);
            return user;
        } else {
            throw new AccountByEmailNotFoundException(email);
        }
    }

    @Override
    public Account getAccount(UUID id) {
        return accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
    }

    @Override
    public Account addAccount(String email, String password, String role) {
        if (accountRepository.existsByEmail(email)) {
            throw new EmailExistsException("Email is already exists");
        } else {
            Account account = new Account(email, BCrypt.hashpw(password, salt), role);
            accountRepository.save(account);
            return account;
        }
    }

    @Override
    public Account updateAccount(UUID id, String email, String password) {
        Optional<Account> account = accountRepository.findById(id);

        if (account.isPresent()) {
            if (!account.get().getPassword().equals(password)) {
                account.get().setPassword(BCrypt.hashpw(password, salt));
            }
            if (!account.get().getEmail().equals(email)) {
                if (!accountRepository.existsByEmail(email)) {
                    account.get().setEmail(email);
                } else {
                    throw new EmailExistsException("Username already exists");
                }
            }
            accountRepository.save(account.get());
            return account.get();
        } else {
            throw new AccountNotFoundException("Account was not found");
        }
    }

    @Override
    public void deleteAccount(UUID id) throws AccountNotFoundException {
        if (accountRepository.findById(id).isPresent()) {
            accountRepository.deleteById(id);
        } else {
            throw new AccountNotFoundException("Account was not found");
        }
    }


}
