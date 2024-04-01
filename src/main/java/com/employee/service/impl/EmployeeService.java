package com.employee.service.impl;

import com.employee.entity.BasicDetail;
import com.employee.entity.Employee;
import com.employee.exception.GenericException;
import com.employee.exception.ResourceNotFoundException;
import com.employee.feign.DepartmentFeign;
import com.employee.repository.EmployeeGarbageRepository;
import com.employee.repository.EmployeeJDBCRepository;
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

    @Autowired
    private EmployeeGarbageRepository employeeGarbageRepository;

    @Autowired
    private EmployeeJDBCRepository employeeJDBCRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @Override
    public Employee createEmployee(Employee employee) {
        LOGGER.info("Saving employee object on the DB: {}", employee);
        return employeeRepository.save(employee);
    }

    @Override
    public List<BasicDetail> getEmployees() {
        LOGGER.info("Fetching employee list from the DB...");
        List<BasicDetail> empBasicDetails = employeeJDBCRepository.getEmployeeBasicDetails();
        if(Objects.isNull(empBasicDetails) || empBasicDetails.isEmpty()) {
            LOGGER.error("No Records found in the database!!");
            throw new ResourceNotFoundException("Employee List is empty");
        }
        LOGGER.info("Fetch operation completed!!");
        return empBasicDetails;
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

    @Override
    public Employee updateEmployee(Employee employee) {
        LOGGER.info("Checking whether the employee object exists on the DB for the given employee ID: {}", employee.getEmployeeId());
        employeeRepository.findById(employee.getEmployeeId()).orElseThrow(() -> {
            LOGGER.error("No Record found for the given employee ID: {}", employee.getEmployeeId());
            throw new ResourceNotFoundException("Employee with given employee ID is not found on the DB: " + employee.getEmployeeId());
        });
        LOGGER.info("Record found | Updating employee object on the DB for employee ID: {}", employee.getEmployeeId());
        return employeeRepository.save(employee);
    }

    @Override
    public void deleteEmployee(String empId) {
        LOGGER.info("Checking whether the employee object exists on the DB for the given employee ID: {}", empId);
        employeeRepository.findById(empId).orElseThrow(() -> {
            LOGGER.error("No Record found for the given employee ID: {}", empId);
            throw new ResourceNotFoundException("Employee with given employee ID is not found on the DB: " + empId);
        });
        LOGGER.info("Record found | Persisting employee ID: {} before deleting it from employeedb.employee table", empId);
        int row = employeeGarbageRepository.saveEmpIdInGarbageTable(empId);
        if(row > 0) {
            LOGGER.info("Employee ID {} persisted in employeedb.emp_garbage_tbl", empId);
        } else {
            LOGGER.error("Employee ID {} could not be persisted in employeedb.emp_garbage_tbl", empId);
            throw new GenericException("Unable to persist employee id " + empId + " in employeedb.emp_garbage_tbl");
        }
        LOGGER.info("Deleting employee {} from employeedb.employee table", empId);
        employeeRepository.deleteById(empId);
    }
}
