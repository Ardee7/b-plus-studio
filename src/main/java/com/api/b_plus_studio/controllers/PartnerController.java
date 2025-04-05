package com.api.b_plus_studio.controllers;

import com.api.b_plus_studio.dtos.GenericResponse;
import com.api.b_plus_studio.dtos.UserRequest;
import com.api.b_plus_studio.dtos.partners.CreatePartnerRequest;
import com.api.b_plus_studio.services.PartnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/partner")
@RequiredArgsConstructor
@Slf4j
public class PartnerController {

    private final PartnerService partnerService;

    @Operation(summary = "Create a new partner", description = "Creates a new partner with the provided details.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Partner created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity createPartner(@Validated @RequestBody CreatePartnerRequest request) {
        return new ResponseEntity<>(GenericResponse.build(partnerService.createPartner(request)), HttpStatus.OK);
    }

    @Operation(summary = "Get all partners", description = "Retrieves a paginated list of all partners.")
    @ApiResponse(responseCode = "200", description = "List of partners retrieved successfully")
    @GetMapping
    public ResponseEntity getAllPartner(
            @RequestParam(defaultValue = "0") int page,     // page number
            @RequestParam(defaultValue = "10") int size,    // page size
            @RequestParam(defaultValue = "createdAt") String sortBy // optional sorting
    ) {
        return new ResponseEntity<>(GenericResponse.build(partnerService.getAllPartners(page, size,sortBy)), HttpStatus.OK);
    }

    @Operation(summary = "Update a partner", description = "Updates the details of an existing partner.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PatchMapping("/{id}")
    public ResponseEntity updatePartner(@PathVariable UUID id, @Validated @RequestBody CreatePartnerRequest request) {
        return new ResponseEntity<>(GenericResponse.build(partnerService.updateUser(id, request)), HttpStatus.OK);
    }

    @Operation(summary = "Delete a partner", description = "Deletes a partner by their ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Partner deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Partner not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deletePartner(@PathVariable UUID id) {
        return new ResponseEntity<>(GenericResponse.build(partnerService.deletePartner(id)), HttpStatus.OK);
    }

}
