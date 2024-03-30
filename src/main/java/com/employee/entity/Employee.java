package com.employee.entity;

import com.employee.constant.EmployeeConstant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@Entity
@ToString
@Table(name = "employee")
public class Employee {

    @Id
    @GenericGenerator(name = "emp_seq", strategy = "com.employee.config.EmployeeIdSequenceGenerator")
    @GeneratedValue(generator = "emp_seq")
    @Schema(hidden = true)
    private String employeeId;

    @NotEmpty(message = EmployeeConstant.EMP_FNAME_MSG)
    @Schema(type = "string", example = "Clark")
    private String firstName;

    @NotEmpty(message = EmployeeConstant.EMP_LNAME_MSG)
    @Schema(type = "string", example = "Kent")
    private String lastName;

    @NotEmpty(message = EmployeeConstant.EMP_ROLE_MSG)
    @Schema(type = "string", example = "UI Developer")
    private String role;

    @NotEmpty(message = EmployeeConstant.EMP_DSGN_MSG)
    @Schema(type = "string", example = "Associate Consultant")
    private String designation;

    @Pattern(regexp = EmployeeConstant.EMP_GENDER_REGEXP, message = EmployeeConstant.EMP_GENDER_REGEXP_MSG)
    @Schema(type = "string", example = "Male")
    private String gender;

    @Schema(type = "double", example = "1200000")
    private double salary;

    @Size(min = 8, max = 50, message = EmployeeConstant.EMP_ADDRESS_SIZE_MSG)
    @Schema(type = "string", example = "12/5 XYZ Street, LA")
    private String address;

    @Email(regexp = EmployeeConstant.EMP_EMAIL_REGEXP, flags = Pattern.Flag.CASE_INSENSITIVE, message = EmployeeConstant.EMP_EMAIL_REGEXP_MSG)
    @Schema(type = "string", example = "clark.kent@example.com")
    private String emailId;

    @Pattern(regexp = EmployeeConstant.EMP_IMG_REGEXP, message = EmployeeConstant.EMP_IMG_REGEXP_MSG)
    @Schema(type = "string", example = "https://sample-image-url.jpg")
    private String image;

    @Pattern(regexp = EmployeeConstant.EMP_MOBILE_REGEXP, message = EmployeeConstant.EMP_MOBILE_REGEXP_MSG)
    @Schema(type = "string", example = "8977531224")
    private String mobileNo;

    @Pattern(regexp = EmployeeConstant.EMP_STATUS_REGEXP, message = EmployeeConstant.EMP_STATUS_REGEXP_MSG)
    @Schema(type = "string", example = "Single")
    private String maritalStatus;

    @Size(min = 8, max = 255, message = EmployeeConstant.EMP_INFO_MSG)
    @Schema(type = "string", example = "Clark Kent sample profile info")
    private String employeeInfo;

    @Transient
    @Schema(hidden = true)
    private Department department;
}
