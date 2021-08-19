package com.mockproject.quizweb.repository;

import com.mockproject.quizweb.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findAccountByUsername(String username);
}