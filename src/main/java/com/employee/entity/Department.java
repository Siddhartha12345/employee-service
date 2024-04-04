package com.employee.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Department {

    @Schema(type = "string", example = "DID1")
    private String departmentId;

    @Schema(type = "string", example = "Information Technology")
    private String departmentName;

    @Schema(type = "string", example = "Bruce Wayne")
    private String departmentHead;

    @Schema(type = "string", example = "https://department-sample-logo.png")
    private String departmentLogo;
}
