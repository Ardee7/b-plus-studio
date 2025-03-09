package com.api.b_plus_studio.services.impl;

import com.api.b_plus_studio.entities.Category;
import com.api.b_plus_studio.repositories.CategoryRepository;
import com.api.b_plus_studio.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Category not found"));
    }
}
