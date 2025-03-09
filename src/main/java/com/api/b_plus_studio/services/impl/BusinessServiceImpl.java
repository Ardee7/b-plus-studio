package com.api.b_plus_studio.services.impl;

import com.api.b_plus_studio.dtos.BusinessRequest;
import com.api.b_plus_studio.entities.Business;
import com.api.b_plus_studio.entities.Category;
import com.api.b_plus_studio.entities.User;
import com.api.b_plus_studio.repositories.BusinessRepository;
import com.api.b_plus_studio.services.BusinessService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {

    private final BusinessRepository businessRepository;

    @Transactional
    public Business createBusiness(User owner, BusinessRequest request, Category category) {
        Business business = Business.builder()
                .owner(owner)
                .name(request.getName())
                .description(request.getDescription())
                .category(category)
                .logo(request.getLogo())
                .build();

        return businessRepository.save(business);
    }

}
