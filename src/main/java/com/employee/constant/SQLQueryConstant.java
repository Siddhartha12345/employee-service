package com.employee.constant;

public class SQLQueryConstant {

    private SQLQueryConstant() {}

    public static String GARBAGE_TBL_INSERT_EMPID_QUERY = "INSERT INTO employeedb.emp_garbage_tbl (employee_id) VALUES(?)";

    public static String EMP_TBL_FETCH_BASIC_DETAIL_QUERY = "SELECT employee_id, first_name, last_name, role, image from employeedb.employee" + " ORDER BY CAST(SUBSTRING(employee_id, LOCATE('D', employee_id)+1) AS SIGNED)";
}
