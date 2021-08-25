package com.mockproject.quizweb.service;

import com.mockproject.quizweb.domain.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    void create(Category category);

    Optional<Category> findByName(String name);

    List<Category> getAllCategory();
}
