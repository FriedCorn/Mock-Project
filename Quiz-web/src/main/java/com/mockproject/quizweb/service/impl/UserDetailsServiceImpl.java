package com.mockproject.quizweb.service.impl;

import com.mockproject.quizweb.domain.Account;
import com.mockproject.quizweb.repository.AccountRepository;
import com.mockproject.quizweb.service.AccountService;
import com.mockproject.quizweb.service.ListRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final AccountRepository accountRepository;
    private final ListRoleService listRoleService;

    @Autowired
    public UserDetailsServiceImpl(AccountRepository accountRepository, ListRoleService listRoleService) {
        this.accountRepository = accountRepository;
        this.listRoleService = listRoleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account userAccount = accountRepository.findAccountByUsername(username);

        if (userAccount == null) {
            System.out.println("User not found! " + username);
            throw new UsernameNotFoundException("User " + username + " was not found in the database");
        }

        System.out.println("Found User: " + userAccount.getUsername());

        // [ROLE_USER, ROLE_ADMIN,..]
        List<String> roleNames = this.listRoleService.getRolesName(userAccount.getUsername());

        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roleNames != null) {
            for (String role : roleNames) {
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }

        return (UserDetails) new User(userAccount.getUsername(), userAccount.getPassword(), grantList);
    }
}
