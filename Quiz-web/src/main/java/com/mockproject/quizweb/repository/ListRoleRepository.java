package com.mockproject.quizweb.repository;

import com.mockproject.quizweb.domain.ListRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ListRoleRepository extends JpaRepository<ListRole, Long> {
    List<ListRole> findListRolesByAccount_Username(String username);
}
