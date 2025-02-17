package com.employee.service.impl;

import com.employee.constant.EmployeeErrorEnum;
import com.employee.entity.BasicDetail;
import com.employee.entity.Employee;
import com.employee.exception.GenericException;
import com.employee.exception.ResourceNotFoundException;
import com.employee.feign.DepartmentFeign;
import com.employee.repository.EmployeeGarbageRepository;
import com.employee.repository.EmployeeJDBCRepository;
import com.employee.repository.EmployeeJPARepository;
import com.employee.service.IEmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class EmployeeService implements IEmployeeService {

    @Autowired
    private DepartmentFeign departmentFeign;

    @Autowired
    private EmployeeJPARepository employeeJPARepository;

    @Autowired
    private EmployeeGarbageRepository employeeGarbageRepository;

    @Autowired
    private EmployeeJDBCRepository employeeJDBCRepository;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @Override
    @Caching(
            evict = { @CacheEvict(value = "empBasicDetails", allEntries = true) },
            put = { @CachePut(value = "employee", key = "#employee.employeeId") }
    )
    public Employee createEmployee(Employee employee) {
        LOGGER.info("Saving employee object on the DB: {}", employee);
        return employeeJPARepository.save(employee);
    }

    @Override
    @Cacheable(value = "empBasicDetails")
    public List<BasicDetail> getEmployees() {
        LOGGER.info("Fetching employee list from the DB...");
        List<BasicDetail> empBasicDetails = employeeJDBCRepository.getEmployeeBasicDetails();
        if(Objects.isNull(empBasicDetails) || empBasicDetails.isEmpty()) {
            LOGGER.error("No Records found in the database!!");
            throw new ResourceNotFoundException(EmployeeErrorEnum.EMP_EMPTY_LIST.getErrorCode(), EmployeeErrorEnum.EMP_EMPTY_LIST.getErrorMessage());
        }
        LOGGER.info("Fetch operation completed!!");
        return empBasicDetails;
    }

    @Override
    @Cacheable(value = "employee", key = "#empId")
    public Employee getEmployeeDetail(String empId) {
        LOGGER.info("Fetching employee object for the given Emp ID: {}", empId);
        Employee employee = employeeJPARepository.findById(empId).orElseThrow(() -> {
            LOGGER.error("No Record found for the given employee ID: {}", empId);
            throw new ResourceNotFoundException(EmployeeErrorEnum.EMP_NOT_FOUND.getErrorCode(), EmployeeErrorEnum.EMP_NOT_FOUND.getErrorMessage());
        });
        LOGGER.info("Fetch operation completed!!");
        return employee;
    }

    @Override
    @Caching(
            evict = { @CacheEvict(value = "empBasicDetails", allEntries = true) },
            put = { @CachePut(value = "employee", key = "#employee.employeeId") }
    )
    public Employee updateEmployee(Employee employee) {
        LOGGER.info("Checking whether the employee object exists on the DB for the given employee ID: {}", employee.getEmployeeId());
        employeeJPARepository.findById(employee.getEmployeeId()).orElseThrow(() -> {
            LOGGER.error("No Record found for the given employee ID: {}", employee.getEmployeeId());
            throw new ResourceNotFoundException(EmployeeErrorEnum.EMP_NOT_FOUND.getErrorCode(), EmployeeErrorEnum.EMP_NOT_FOUND.getErrorMessage());
        });
        LOGGER.info("Record found | Updating employee object on the DB for employee ID: {}", employee.getEmployeeId());
        return employeeJPARepository.save(employee);
    }

    @Override
    @Caching(evict = {@CacheEvict(value = "empBasicDetails", allEntries = true)})
    @CacheEvict(value = "employee", key = "#empId")
    public void deleteEmployee(String empId) {
        LOGGER.info("Checking whether the employee object exists on the DB for the given employee ID: {}", empId);
        employeeJPARepository.findById(empId).orElseThrow(() -> {
            LOGGER.error("No Record found for the given employee ID: {}", empId);
            throw new ResourceNotFoundException(EmployeeErrorEnum.EMP_NOT_FOUND.getErrorCode(), EmployeeErrorEnum.EMP_NOT_FOUND.getErrorMessage());
        });
        LOGGER.info("Record found | Persisting employee ID: {} before deleting it from employeedb.employee table", empId);
        int row = employeeGarbageRepository.saveEmpIdInGarbageTable(empId);
        if(row > 0) {
            LOGGER.info("Employee ID {} persisted in employeedb.emp_garbage_tbl", empId);
        } else {
            LOGGER.error("Employee ID {} could not be persisted in employeedb.emp_garbage_tbl", empId);
            throw new GenericException(EmployeeErrorEnum.GARBAGE_TBL_PERSISTENCE_ERR.getErrorCode(), EmployeeErrorEnum.GARBAGE_TBL_PERSISTENCE_ERR.getErrorMessage());
        }
        LOGGER.info("Deleting employee {} from employeedb.employee table", empId);
        employeeJPARepository.deleteById(empId);
    }
}
