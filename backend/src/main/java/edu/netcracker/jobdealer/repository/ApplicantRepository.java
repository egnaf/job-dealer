package edu.netcracker.jobdealer.repository;

import edu.netcracker.jobdealer.entity.Account;
import edu.netcracker.jobdealer.entity.Applicant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, UUID> {
    Optional<Applicant> findByAccount(Account account);

    Optional<Applicant> findByAccount_Email(String email);

    Optional<Applicant> findById(UUID id);

    void deleteByAccount_Email(String email);

    boolean existsByAccount_Id(UUID id);

}

