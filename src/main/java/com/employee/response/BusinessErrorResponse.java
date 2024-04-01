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
public class BusinessErrorResponse {

    @Schema(type = "string", example = "NOT_FOUND | INTERNAL_SERVER_ERROR")
    private HttpStatus status;

    @Schema(type = "string", example = "Employee not found")
    private String message;
}
