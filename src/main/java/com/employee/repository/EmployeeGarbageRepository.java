package com.employee.repository;

import com.employee.constant.SQLQueryConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeGarbageRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int saveEmpIdInGarbageTable(String empId) {
        return jdbcTemplate.update(SQLQueryConstant.GARBAGE_TBL_INSERT_EMPID_QUERY, empId);
    }
}
