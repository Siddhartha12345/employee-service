package com.employee.constant;

public class EmployeeConstant {

    private EmployeeConstant() {}

    // Validation messages
    public static final String EMP_FNAME_MSG = "Employee firstname cannot be empty";
    public static final String EMP_LNAME_MSG = "Employee lastname cannot be empty";
    public static final String EMP_ADDRESS_SIZE_MSG = "Employee address should be of min 8 and max 50 characters";
    public static final String EMP_EMAIL_REGEXP_MSG = "Invalid Employee Email";
    public static final String EMP_IMG_REGEXP_MSG = "Invalid Employee Image";
    public static final String EMP_MOBILE_REGEXP_MSG = "Mobile number should contain 10 digits";
    public static final String EMP_STATUS_REGEXP_MSG = "Marital status value should be either Single, Married or Divorced";
    public static final String EMP_ID_REGEXP_MSG = "Employee ID should start with EID followed by digit(s)";
    public static final String EMP_ROLE_MSG = "Employee role cannot be empty";
    public static final String EMP_DSGN_MSG = "Employee designation cannot be empty";
    public static final String EMP_GENDER_REGEXP_MSG = "Gender should be either Male or Female";
    public static final String EMP_INFO_MSG = "Employee info should be in range between 8 and 255 characters";

    // Regular expressions
    public static final String EMP_EMAIL_REGEXP = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}";
    public static final String EMP_IMG_REGEXP = "(?:([^:/?#]+):)?(?://([^/?#]*))?([^?#]*\\.(?:jpg|png))(?:\\?([^#]*))?(?:#(.*))?";
    public static final String EMP_MOBILE_REGEXP = "^[0-9]{10}$";
    public static final String EMP_STATUS_REGEXP = "^(Single|Married|Divorced)$";
    public static final String EMP_GENDER_REGEXP = "^(Male|Female)$";
    public static final String EMP_ID_REGEXP = "^EID[0-9]+";

    // openapi constants
    public static final String OPENAPI_GET_EMPLIST_SUMMARY = "Get all employees";
    public static final String OPENAPI_GET_EMPLIST_200_MSG = "Retrieved all employee records";
    public static final String OPENAPI_GET_EMPLIST_404_MSG = "Employee list found empty";
    public static final String OPENAPI_GET_EMP_SUMMARY = "Get an employee by employee ID";
    public static final String OPENAPI_GET_EMP_200_MSG = "Found the employee";
    public static final String OPENAPI_400_MSG = "Validation error in the request";
    public static final String OPENAPI_404_MSG = "Employee not found";
    public static final String OPENAPI_POST_EMP_SUMMARY = "Add an employee";
    public static final String OPENAPI_POST_EMP_201_MSG = "Employee added successfully";
    public static final String OPENAPI_PUT_EMP_SUMMARY = "Update an employee";
    public static final String OPENAPI_PUT_EMP_200_MSG = "Employee details updated successfully";
    public static final String OPENAPI_DELETE_EMP_SUMMARY = "Delete an employee";
    public static final String OPENAPI_DELETE_EMP_200_MSG = "Employee deleted successfully";
    public static final String OPENAPI_DELETE_EMP_500_MSG = "Error while persisting deleted employee ID in garbage table";
    public static final String OPENAPI_EMPID_MSG = "Employee ID";

    // HTTP status codes
    public static final String HTTP_OK_CODE = "200";
    public static final String HTTP_BAD_REQ_CODE = "400";
    public static final String HTTP_NOT_FOUND_CODE = "404";
    public static final String HTTP_INTERNAL_SERVER_ERR_CODE = "500";
    public static final String HTTP_CREATED_CODE = "201";
}