package com.mockproject.quizweb.service;

import com.mockproject.quizweb.domain.Account;

import java.util.List;

public interface AccountService {
    void save(Account account);
    void deleteById(long id);
    List<Account> findAll();
    Account getById(long id);
    Account findAccountByUsername(String username);
}
