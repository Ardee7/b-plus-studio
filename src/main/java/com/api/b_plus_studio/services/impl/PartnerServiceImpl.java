package com.api.b_plus_studio.services.impl;

import com.api.b_plus_studio.Utilities.PojoUtils;
import com.api.b_plus_studio.dtos.UserRequest;
import com.api.b_plus_studio.dtos.UserResponse;
import com.api.b_plus_studio.dtos.partners.CreatePartnerRequest;
import com.api.b_plus_studio.dtos.partners.PartnerResponse;
import com.api.b_plus_studio.entities.User;
import com.api.b_plus_studio.enums.Role;
import com.api.b_plus_studio.enums.UserStatus;
import com.api.b_plus_studio.repositories.UserRepository;
import com.api.b_plus_studio.services.PartnerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PartnerServiceImpl implements PartnerService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PagedResourcesAssembler<PartnerResponse> pagedResourcesAssembler;


    @Override
    public PagedModel<EntityModel<PartnerResponse>> getAllPartners(int page, int size, String sortBy) {
        log.info("Getting all pageable users.");
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        log.info("JPA find all by role repository function conducted.");
        Page<User> partners = userRepository.findAllByRoleAndStatusNot(Role.PARTNER, UserStatus.DELETED, pageable);

        log.info("Mapping users from user raw entities to partner response dto.");
        Page<PartnerResponse> pagePartners = partners.map(user -> PartnerResponse.builder().id(user.getId()).email(user.getEmail()).firstname(user.getFirstname()).lastname(user.getLastname()).status(user.getStatus()).build());

        return pagedResourcesAssembler.toModel(pagePartners);
    }

    @Override
    public PartnerResponse createPartner(CreatePartnerRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .username(request.getEmail())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .passwordHash(request.getPassword() != null ? passwordEncoder.encode(request.getPassword()) : null)
                .role(Role.PARTNER)
                .status(UserStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        log.info("User with first name of {} and last name of {} is created.", request.getFirstname(), request.getLastname());
        User savedUser = userRepository.save(user);

        return PartnerResponse.builder().id(savedUser.getId()).firstname(savedUser.getFirstname()).lastname(savedUser.getLastname()).email(savedUser.getEmail()).status(savedUser.getStatus()).build();
    }

    @Override
    public PartnerResponse updateUser(UUID id, CreatePartnerRequest request) {
        log.info("Fetching user with an id of {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        log.info("User with an id of {} fetched. Updating fields...", id);
        user.setUpdatedAt(LocalDateTime.now());
        PojoUtils.copyNonNullProperties(request, user);

        if (request.getPassword() != null) {
            user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        }

        User savedUser = userRepository.save(user);
        log.info("User with an id of {} updated.", id);

        return PartnerResponse.builder().id(savedUser.getId()).firstname(savedUser.getFirstname()).lastname(savedUser.getLastname()).email(savedUser.getEmail()).status(savedUser.getStatus()).build();
    }

    @Override
    public String deletePartner(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Partner with ID " + id + " not found"));

        user.setStatus(UserStatus.DELETED);

        try {
            userRepository.save(user);
            log.info("Partner with an id of {} deletion succeeded", id);
            return "Partner deleted successfully"; // Success message
        } catch (Exception e) {
            // In case something goes wrong with the save operation, log and throw an exception
            log.info("Error deleting partner with ID of {}.", id);
            throw new RuntimeException("Failed to delete partner. Please try again later.");
        }
    }
}
