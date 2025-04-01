package com.api.b_plus_studio.dtos;

import com.api.b_plus_studio.Utilities.DateUtils;
import com.api.b_plus_studio.constants.Globals;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
public class GenericResponse<T> {

    private final String programName;
    private final String dateTime;
    private final T body;

    public static <T> GenericResponse<T> build(T body) {
        return GenericResponse.<T>builder()
                .programName(Globals.PROGRAM_NAME)
                .dateTime(DateUtils.getCurrentDateInResponseFormat())
                .body(body)
                .build();
    }
}