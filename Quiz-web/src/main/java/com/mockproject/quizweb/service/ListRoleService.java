package com.mockproject.quizweb.service;

import com.mockproject.quizweb.domain.ListRole;

import java.util.List;

public interface ListRoleService {
    void save(ListRole listRole);
    List<ListRole> findListRolesByAccount_Username(String username);
    List<String> getRolesName(String username);
}
