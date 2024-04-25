package com.employee.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicDetail implements Serializable {

    @Schema(type = "string", example = "EID1")
    private String employeeId;

    @Schema(type = "string", example = "Clark")
    private String firstName;

    @Schema(type = "string", example = "Kent")
    private String lastName;

    @Schema(type = "string", example = "UI Developer")
    private String role;

    @Schema(type = "string", example = "https://sample-image-url.jpg")
    private String image;

    private Department department;
}
