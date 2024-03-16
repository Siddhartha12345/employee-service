package com.employee.service.impl;

import com.employee.entity.Employee;
import com.employee.exception.ResourceNotFoundException;
import com.employee.feign.DepartmentFeign;
import com.employee.repository.EmployeeRepository;
import com.employee.service.IEmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private DepartmentFeign departmentFeign;

    @Autowired
    private EmployeeRepository employeeRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @Override
    public Employee createEmployee(Employee employee) {
        LOGGER.info("Saving employee object on the DB: {}", employee);
        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getEmployees() {
        LOGGER.info("Fetching employee list from the DB...");
        List<Employee> employees = employeeRepository.findAll();
        if(Objects.isNull(employees) || employees.size() == 0) {
            LOGGER.error("No Records found in the database!!");
            throw new ResourceNotFoundException("Employee List is empty");
        }
        LOGGER.info("Fetch operation completed!!");
        return employees;
    }

    @Override
    public Employee getEmployeeDetail(String empId) {
        LOGGER.info("Fetching employee object for the given Emp ID: {}", empId);
        Employee employee = employeeRepository.findById(empId).orElseThrow(() -> {
            LOGGER.error("No Record found for the given employee ID: {}", empId);
            throw new ResourceNotFoundException("Employee with given Emp ID is not found on the DB: " + empId);
        });
        LOGGER.info("Fetch operation completed!!");
        return employee;
    }
}
