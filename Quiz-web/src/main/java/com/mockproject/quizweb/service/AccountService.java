package com.mockproject.quizweb.service;

import com.mockproject.quizweb.domain.Account;

public interface AccountService {
    Account findAccountByUsername(String username);
}
