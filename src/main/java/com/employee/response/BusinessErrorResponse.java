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
public class BusinessErrorResponse {

    @Schema(type = "string", example = "EMP-SVC-001")
    private String errorCode;

    @Schema(type = "string", example = "Employee List is empty")
    private String errorMessage;
}
