package com.employee.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Department {

    private String empId;

    private String deptId;

    private String deptName;
}
