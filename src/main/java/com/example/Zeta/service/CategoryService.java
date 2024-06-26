package com.example.Zeta.service;

import com.example.Zeta.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> findAll();
    Category save(Category category);
    Optional<Category> findById(Long id);

    Category update(Category category);
    void deleteById(Long id);
    void enabledById(Long id);
}
