package com.employee.entity;

import com.employee.constant.EmployeeConstant;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Employee {

    @NotEmpty(message = EmployeeConstant.EMP_ID_MSG)
    @Pattern(regexp = EmployeeConstant.EMP_ID_REGEXP, message = EmployeeConstant.EMP_ID_REGEXP_MSG)
    private String empId;

    @NotEmpty(message = EmployeeConstant.EMP_NAME_MSG)
    private String empName;

    private Department department;
}
