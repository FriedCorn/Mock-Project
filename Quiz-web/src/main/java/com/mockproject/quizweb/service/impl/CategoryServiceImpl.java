package com.mockproject.quizweb.service.impl;

import com.mockproject.quizweb.domain.Category;
import com.mockproject.quizweb.repository.CategoryRepository;
import com.mockproject.quizweb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
     private final CategoryRepository categoryRepository;

     @Autowired
     public CategoryServiceImpl(CategoryRepository categoryRepository) {
          this.categoryRepository = categoryRepository;
     }


     @Override
     public void create(Category category) {
          categoryRepository.save(category);
     }

     @Override
     public Optional<Category> findByName(String name) {
          return categoryRepository.findByName(name);
     }
}
