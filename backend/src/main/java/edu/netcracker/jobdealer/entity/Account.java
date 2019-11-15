package edu.netcracker.jobdealer.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table
public class Account {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @OneToMany(mappedBy = "messageSource")
    private List<Message> messagesAsSource;

    @OneToMany(mappedBy = "messageDest")
    private List<Message> messagesAsDest;

    @OneToMany(mappedBy = "reviewSource")
    private List<Review> reviewsAsSource;

    @OneToMany(mappedBy = "reviewDest")
    private List<Review> reviewsAsDest;

    public Account() {
    }

    // delete it
    public Account(String password, String mail, String role) {
        this.email = mail;
        this.password = password;
        this.role = role;
        this.username = email;
    }

    public Account(String username, String email, String password, String role) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Account(UUID id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }


}
