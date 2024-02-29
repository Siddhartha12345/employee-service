package com.employee.service.impl;

import com.employee.entity.Department;
import com.employee.entity.Employee;
import com.employee.exception.ResourceNotFoundException;
import com.employee.feign.DepartmentFeign;
import com.employee.service.IEmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private DepartmentFeign departmentFeign;

    private List<Employee> employeeList = new ArrayList<>();

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    public EmployeeService() {
        employeeList.add(new Employee("E01", "Akshay Verma", null));
        employeeList.add(new Employee("E02", "Manish Pandey", null));
    }

    @Override
    public Employee createEmployee(Employee employee) {
        LOGGER.info("Creating employee object on the server...");
        employeeList.add(employee);
        return employee;
    }

    @Override
    public List<Employee> getEmployees() {
        LOGGER.info("Fetching employee list from the server...");
        List<Employee> employees = employeeList.stream().map(
                emp -> {
                    LOGGER.info("Fetching department for Employee: {}", emp.getEmpId());
                    Department department = departmentFeign.getDepartmentByEmpId((emp.getEmpId()));
                    emp.setDepartment(department);
                    return emp;
                }
        ).collect(Collectors.toList());
        return employees;
    }

    @Override
    public Employee getEmployeeDetail(String empId) {
        LOGGER.info("Fetching employee object for the given Emp ID: {}", empId);
        Employee employee = employeeList.stream().filter(e -> e.getEmpId().equals(empId))
                .findAny().orElseThrow(() -> {
                    LOGGER.error("Employee with given Emp ID is not found on the server: {}", empId);
                    throw new ResourceNotFoundException("Employee with given Emp ID is not found on the server: " + empId);
                });
        LOGGER.info("Fetching department info for Employee: {}", empId);
        Department department = departmentFeign.getDepartmentByEmpId(empId);
        employee.setDepartment(department);
        return employee;
    }
}
