package com.api.b_plus_studio.services;

import com.api.b_plus_studio.dtos.partners.CreatePartnerRequest;
import com.api.b_plus_studio.dtos.partners.PartnerResponse;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;

import java.util.UUID;

public interface PartnerService {

    PagedModel<EntityModel<PartnerResponse>> getAllPartners(int page, int size, String sortBy);

    PartnerResponse createPartner(CreatePartnerRequest request);

    PartnerResponse updateUser(UUID id, CreatePartnerRequest request);

    String deletePartner(UUID id);
}
