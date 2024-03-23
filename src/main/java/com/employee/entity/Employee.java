package com.employee.entity;

import com.employee.constant.EmployeeConstant;
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
    private String employeeId;

    @NotEmpty(message = EmployeeConstant.EMP_FNAME_MSG)
    private String firstName;

    @NotEmpty(message = EmployeeConstant.EMP_LNAME_MSG)
    private String lastName;

    @NotEmpty(message = EmployeeConstant.EMP_ROLE_MSG)
    private String role;

    @NotEmpty(message = EmployeeConstant.EMP_DSGN_MSG)
    private String designation;

    @Pattern(regexp = EmployeeConstant.EMP_GENDER_REGEXP, message = EmployeeConstant.EMP_GENDER_REGEXP_MSG)
    private String gender;

    private double salary;

    @Size(min = 8, max = 50, message = EmployeeConstant.EMP_ADDRESS_SIZE_MSG)
    private String address;

    @Email(regexp = EmployeeConstant.EMP_EMAIL_REGEXP, flags = Pattern.Flag.CASE_INSENSITIVE, message = EmployeeConstant.EMP_EMAIL_REGEXP_MSG)
    private String emailId;

    @Pattern(regexp = EmployeeConstant.EMP_IMG_REGEXP, message = EmployeeConstant.EMP_IMG_REGEXP_MSG)
    private String image;

    @Pattern(regexp = EmployeeConstant.EMP_MOBILE_REGEXP, message = EmployeeConstant.EMP_MOBILE_REGEXP_MSG)
    private String mobileNo;

    @Pattern(regexp = EmployeeConstant.EMP_STATUS_REGEXP, message = EmployeeConstant.EMP_STATUS_REGEXP_MSG)
    private String maritalStatus;

    @Transient
    private Department department;
}
