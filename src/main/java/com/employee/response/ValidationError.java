package com.employee.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ValidationError {

    @Schema(type = "string", example = "BAD_REQUEST")
    private HttpStatus status;

    @Schema(type = "string", example = "fistName")
    private String fieldName;

    @Schema(type = "string", example = "Employee firstName cannot be empty")
    private String message;
}
