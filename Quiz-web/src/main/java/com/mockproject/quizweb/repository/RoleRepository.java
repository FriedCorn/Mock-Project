package com.mockproject.quizweb.repository;

import com.mockproject.quizweb.domain.Role;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}