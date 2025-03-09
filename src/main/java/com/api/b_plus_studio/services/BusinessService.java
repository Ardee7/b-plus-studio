package com.api.b_plus_studio.services;

import com.api.b_plus_studio.dtos.BusinessRequest;
import com.api.b_plus_studio.entities.Business;
import com.api.b_plus_studio.entities.Category;
import com.api.b_plus_studio.entities.User;

public interface BusinessService {
        public Business createBusiness(User owner, BusinessRequest request, Category category);
}