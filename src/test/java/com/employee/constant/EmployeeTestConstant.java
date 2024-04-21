package com.employee.constant;

public class EmployeeTestConstant {

    private EmployeeTestConstant() {}

    public static final String GET_EMP_LIST_ENDPOINT = "/api/v1/employee";

    public static final String POST_EMP_ENDPOINT = "/api/v1/employee";

    public static final String GET_EMP_BY_ID_ENDPOINT = "/api/v1/employee/{empId}";

    public static final String PUT_EMP_ENDPOINT = "/api/v1/employee";

    public static final String DELETE_EMP_ENDPOINT = "/api/v1/employee/{empId}";

    public static final int HTTP_OK_CODE = 200;

    public static final int HTTP_CREATED_CODE = 201;

    public static final int HTTP_BAD_REQUEST_CODE = 400;

    public static final int HTTP_NOT_FOUND_CODE = 404;

    public static final int HTTP_INTERNAL_SERVER_ERR_CODE = 500;

    public static final String EMPTY_LIST_ERR_CODE = "EMP-SVC-001";

    public static final String EMP_NOT_FOUND_ERR_CODE = "EMP-SVC-003";

    public static final String VALIDATION_ERR_CODE = "EMP-SVC-002";

    public static final String GARBAGE_TBL_PERSISTENCE_ERR_CODE = "EMP-SVC-004";
}
