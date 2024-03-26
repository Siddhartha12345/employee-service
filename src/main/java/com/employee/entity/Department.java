package com.employee.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Department {

    private String departmentId;

    private String departmentName;

    private String departmentHead;

    private String departmentLogo;
}
