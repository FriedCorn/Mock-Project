package com.mockproject.quizweb.service.impl;

import com.mockproject.quizweb.domain.ListRole;
import com.mockproject.quizweb.repository.ListRoleRepository;
import com.mockproject.quizweb.service.ListRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ListRoleServiceImpl implements ListRoleService {
    private final ListRoleRepository listRoleRepository;

    @Autowired
    public ListRoleServiceImpl(ListRoleRepository listRoleRepository) {
        this.listRoleRepository = listRoleRepository;
    }

    @Override
    public List<ListRole> findListRolesByAccount_Username(String username) {
        return listRoleRepository.findListRolesByAccount_Username(username);
    }

    @Override
    public List<String> getRolesName(String username) {
        List<ListRole> listRoles = findListRolesByAccount_Username(username);
        List<String> roleNames = new ArrayList<>();
        for (ListRole l: listRoles) {
            roleNames.add(l.getRole().getName());
        }
        return roleNames;
    }
}
