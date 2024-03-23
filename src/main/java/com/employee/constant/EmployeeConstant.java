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

    // Regular expressions
    public static final String EMP_EMAIL_REGEXP = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}";

    public static final String EMP_IMG_REGEXP = "(?:([^:/?#]+):)?(?://([^/?#]*))?([^?#]*\\.(?:jpg|png))(?:\\?([^#]*))?(?:#(.*))?";

    public static final String EMP_MOBILE_REGEXP = "^[0-9]{10}$";

    public static final String EMP_STATUS_REGEXP = "^(Single|Married|Divorced)$";

    public static final String EMP_GENDER_REGEXP = "^(Male|Female)$";

    public static final String EMP_ID_REGEXP = "^EID[0-9]+";
}
