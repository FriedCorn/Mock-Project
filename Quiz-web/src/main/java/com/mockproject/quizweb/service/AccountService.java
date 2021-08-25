package com.mockproject.quizweb.service;

import com.mockproject.quizweb.domain.Account;

import java.util.List;

public interface AccountService {
    void save(Account account);

    void update(Account account);

    void deleteById(int id);

    void delete(Account account);

    List<Account> findAll();

    Account getById(int id);

    Account findAccountByUsername(String username);

    Account findAccountByEmail(String email);
}
