package com.employee.feign;

import com.employee.entity.Department;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "department-svc-feign", url = "http://localhost:8080/dept-svc/api/v1")
public interface DepartmentFeign {

    @GetMapping("/department/employee/{empId}")
    Department getDepartmentByEmpId(@PathVariable String empId);
}
