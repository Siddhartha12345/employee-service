package com.employee.repository;

import com.employee.constant.SQLQueryConstant;
import com.employee.entity.BasicDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeJDBCRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<BasicDetail> getEmployeeBasicDetails() {
        return jdbcTemplate.query(SQLQueryConstant.EMP_TBL_FETCH_BASIC_DETAIL_QUERY, (resultSet, rowNum) -> {
            BasicDetail basicDetail = new BasicDetail();
            basicDetail.setEmployeeId(resultSet.getString("employee_id"));
            basicDetail.setFirstName(resultSet.getString("first_name"));
            basicDetail.setLastName(resultSet.getString("last_name"));
            basicDetail.setRole(resultSet.getString("role"));
            basicDetail.setImage(resultSet.getString("image"));
            return basicDetail;
        });
    }
}
