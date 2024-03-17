package com.employee.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeGarbageRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int saveEmpIdInGarbageTable(String empId) {
        return jdbcTemplate.update("INSERT INTO employeedb.emp_garbage_tbl (employee_id) VALUES(?)", empId);
    }
}
