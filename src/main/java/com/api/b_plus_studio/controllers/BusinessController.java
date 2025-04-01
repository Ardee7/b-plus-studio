package com.api.b_plus_studio.controllers;

import com.api.b_plus_studio.dtos.BusinessRequest;
import com.api.b_plus_studio.entities.Business;
import com.api.b_plus_studio.entities.Category;
import com.api.b_plus_studio.entities.User;
import com.api.b_plus_studio.services.BusinessService;
import com.api.b_plus_studio.services.CategoryService;
import com.api.b_plus_studio.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/businesses")
@RequiredArgsConstructor
@Slf4j
public class BusinessController {

    private final BusinessService businessService;
    private final UserService userService;
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Business> createBusiness(
            @Valid @RequestBody BusinessRequest request,
            Authentication authentication) {

        String email = authentication.getName(); // Get logged-in user's email
        User owner = userService.findByEmail(email);
        Category category = categoryService.findById(request.getCategoryId());

        Business business = businessService.createBusiness(owner, request, category);
        return ResponseEntity.ok(business);
    }
}