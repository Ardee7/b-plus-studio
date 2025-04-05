package com.api.b_plus_studio.dtos.partners;


import com.api.b_plus_studio.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PartnerResponse {
    private UUID id;
    private String firstname;
    private String lastname;
    private String email;
    private UserStatus status;

}
