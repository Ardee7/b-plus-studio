package com.api.b_plus_studio.dtos;

import com.api.b_plus_studio.constants.Globals;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private String code;
    private String message;

    public ExceptionResponse buildGenericError() {
        return new ExceptionResponse(Globals.GENERIC_ERROR_CODE, Globals.STATUS_DESC_INTERNAL_SERVER_ERROR);
    }

}