package com.employee.constant;

import lombok.Getter;

@Getter
public enum EmployeeErrorEnum {

    EMP_EMPTY_LIST("EMP-SVC-001", "Employee List is empty"),
    EMP_VALIDATION_ERR("EMP-SVC-002", "Validation error occurred"),
    EMP_NOT_FOUND("EMP-SVC-003", "Employee with given ID is not found on the DB"),
    GARBAGE_TBL_PERSISTENCE_ERR("EMP-SVC-004", "Unable to persist the given employee ID in the employeedb.emp_garbage_tbl"),
    FEIGN_ERR_NOT_FOUND("EMP-SVC-005", "Department with given ID does not exist on the department service server"),
    GARBAGE_TBL_DELETE_ERR("EMP-SVC-006", "Unable to delete the given employee ID from employeedb.emp_garbage_tbl");

    private String errorCode;
    private String errorMessage;

    private EmployeeErrorEnum(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
