package com.employee.controller;

import com.employee.constant.EmployeeConstant;
import com.employee.entity.Employee;
import com.employee.service.IEmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        LOGGER.info("Execution started for fetching employee list...");
        List<Employee> employeeList = employeeService.getEmployees();
        LOGGER.info("Employee list fetched successfully: {}", employeeList);
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @GetMapping("/employee/{empId}")
    public ResponseEntity<Employee> getEmployee(@Pattern(regexp = EmployeeConstant.EMP_ID_REGEXP, message = EmployeeConstant.EMP_ID_REGEXP_MSG)
                                                @PathVariable String empId) {
        LOGGER.info("Execution started for fetching employee object...");
        Employee employee = employeeService.getEmployeeDetail(empId);
        LOGGER.info("Employee object fetched successfully: {}", employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping("/employee")
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee) {
        LOGGER.info("Execution started for creating employee object...");
        Employee createdEmployee = employeeService.createEmployee(employee);
        LOGGER.info("Employee object created successfully: {}", employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }
}
