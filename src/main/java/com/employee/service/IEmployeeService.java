package com.employee.service;

import com.employee.entity.Employee;

import java.util.List;

public interface IEmployeeService {

    Employee createEmployee(Employee employee);

    List<Employee> getEmployees();

    Employee getEmployeeDetail(String empId);

    Employee updateEmployee(Employee employee);

    void deleteEmployee(String empId);
}
