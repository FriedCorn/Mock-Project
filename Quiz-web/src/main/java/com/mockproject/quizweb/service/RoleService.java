package com.mockproject.quizweb.service;

import com.mockproject.quizweb.domain.Role;

public interface RoleService {
    Role findByName(String name);
}
