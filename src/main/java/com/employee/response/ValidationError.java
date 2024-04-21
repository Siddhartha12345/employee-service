package com.employee.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ValidationError {

    @Schema(type = "string", example = "EMP-SVC-002")
    private String errorCode;

    @Schema(type = "string", example = "fistName")
    private String fieldName;

    @Schema(type = "string", example = "Employee firstName cannot be empty")
    private String errorMessage;
}
