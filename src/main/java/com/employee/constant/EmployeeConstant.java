package com.employee.constant;

public class EmployeeConstant {

    private EmployeeConstant() {}

    public static final String EMP_ID_MSG = "Employee ID cannot be empty";

    public static final String EMP_NAME_MSG = "Employee name cannot be empty";

    public static final String EMP_ID_REGEXP_MSG = "Employee ID should start with E followed by 2 digits";

    public static final String EMP_ID_REGEXP = "^[E][0-9]{2}$";
}
