package com.mockproject.quizweb.service.impl;

import com.mockproject.quizweb.domain.Role;
import com.mockproject.quizweb.repository.RoleRepository;
import com.mockproject.quizweb.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
    
    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }
}
