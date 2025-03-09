package com.api.b_plus_studio.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BusinessRequest {

    @NotBlank(message = "Business name is required")
    private String name;

    private String description;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    private String logo;
}