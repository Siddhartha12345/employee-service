package com.employee.config;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EmployeeIdSequenceGenerator implements IdentifierGenerator {

    private static final String prefix = "EID";

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeIdSequenceGenerator.class);

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object object) {
        try(Connection connection = session.getJdbcConnectionAccess().obtainConnection();) {
            Statement statement=connection.createStatement();
            ResultSet rs=statement.executeQuery("select count(employee_id) as Id from employeedb.employee");
            if(rs.next()) {
                int id = rs.getInt(1) + 1;
                String generatedId = prefix + id;
                LOGGER.info("Generated Id: {}", generatedId);
                return generatedId;
            }
        } catch (SQLException e) {
            LOGGER.error("Encountered exception while generating sequence for employee ID: {}", e);
        }
        return null;
    }
}
