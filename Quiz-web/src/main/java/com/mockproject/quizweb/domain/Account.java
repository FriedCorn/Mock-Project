package com.mockproject.quizweb.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "account")
public class Account {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "username", length = 40, unique = true)
    @NotNull
    @NotEmpty
    private String username;

    @Column(name = "password")
    @NotNull
    @NotEmpty
    private String password;

    @Column(name = "email", length = 80, unique = true)
    @NotNull
    @NotEmpty
    private String email;

    @Column(name = "first_name", length = 30)
    @NotNull
    @NotEmpty
    private String firstName;

    @Column(name = "last_name", length = 30)
    @NotNull
    @NotEmpty
    private String lastName;

    @Column(name = "dob", length = 30)
    @NotNull
    @NotEmpty
    private String DoB;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Account account = (Account) o;

        return Objects.equals(id, account.id);
    }

    @Override
    public int hashCode() {
        return 2083479647;
    }
}
