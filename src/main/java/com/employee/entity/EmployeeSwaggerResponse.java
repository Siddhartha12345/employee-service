package com.employee.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSwaggerResponse {

    @Schema(type = "string", example = "EID1")
    private String employeeId;

    @Schema(type = "string", example = "Clark")
    private String firstName;

    @Schema(type = "string", example = "Kent")
    private String lastName;

    @Schema(type = "string", example = "UI Developer")
    private String role;

    @Schema(type = "string", example = "Associate Consultant")
    private String designation;

    @Schema(type = "string", example = "Male")
    private String gender;

    @Schema(type = "double", example = "1200000")
    private double salary;

    @Schema(type = "string", example = "12/5 XYZ Street, LA")
    private String address;

    @Schema(type = "string", example = "clark.kent@example.com")
    private String emailId;

    @Schema(type = "string", example = "https://sample-image-url.jpg")
    private String image;

    @Schema(type = "string", example = "8977531224")
    private String mobileNo;

    @Schema(type = "string", example = "Single")
    private String maritalStatus;

    @Schema(type = "string", example = "Clark Kent sample profile info")
    private String employeeInfo;

    private Department department;
}
