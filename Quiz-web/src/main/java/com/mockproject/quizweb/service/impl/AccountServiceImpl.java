package com.mockproject.quizweb.service.impl;

import com.mockproject.quizweb.domain.Account;
import com.mockproject.quizweb.repository.AccountRepository;
import com.mockproject.quizweb.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.accountRepository = accountRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void save(Account account) {
        account.setPassword(bCryptPasswordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
    }

    @Override
    public void deleteById(int id) {
        accountRepository.deleteById(id);
    }

    @Override
    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account getById(int id) {
        return accountRepository.findById(id).orElse(null);
    }

    @Override
    public Account findAccountByUsername(String username) {
        return accountRepository.findAccountByUsername(username);
    }

    @Override
    public void delete(Account deleteAccount) {
        Account foundAccount = accountRepository.findAccountByUsername(deleteAccount.getUsername());

        if (foundAccount != null)
            accountRepository.deleteById(foundAccount.getId());
    }

    @Override
    public void update(Account updateAccount) {
        Account foundAccount = accountRepository.findAccountByUsername(updateAccount.getUsername());
        updateAccount.setId(foundAccount.getId());

        if (updateAccount.getPassword().equals("")) {
            updateAccount.setPassword(foundAccount.getPassword());

            accountRepository.save(updateAccount);
        } else {
            updateAccount.setPassword(bCryptPasswordEncoder.encode(updateAccount.getPassword()));
            accountRepository.save(updateAccount);
        }
    }

    @Override
    public Account findAccountByEmail(String email) {
        return accountRepository.findAccountByEmail(email);
    }
}
