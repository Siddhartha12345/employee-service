package com.employee.controller;

import com.employee.constant.EmployeeConstant;
import com.employee.entity.Employee;
import com.employee.response.BusinessErrorResponse;
import com.employee.response.ValidationErrorResponse;
import com.employee.service.IEmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/api/v1")
@Tag(name = "Employee CRUD APIs", description = "Perform basic create, read, update and delete operations for employees")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @Operation(summary = EmployeeConstant.OPENAPI_GET_EMPLIST_SUMMARY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = EmployeeConstant.HTTP_OK_CODE, description = EmployeeConstant.OPENAPI_GET_EMPLIST_200_MSG, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = Employee.class)))}),
            @ApiResponse(responseCode = EmployeeConstant.HTTP_NOT_FOUND_CODE, description = EmployeeConstant.OPENAPI_GET_EMPLIST_404_MSG, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BusinessErrorResponse.class))})
    })
    @GetMapping("/employee")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        LOGGER.info("Execution started for fetching employee list...");
        List<Employee> employeeList = employeeService.getEmployees();
        LOGGER.info("Employee list fetched successfully: {}", employeeList);
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }

    @Operation(summary = EmployeeConstant.OPENAPI_GET_EMP_SUMMARY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = EmployeeConstant.HTTP_OK_CODE, description = EmployeeConstant.OPENAPI_GET_EMP_200_MSG, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = EmployeeConstant.HTTP_BAD_REQ_CODE, description = EmployeeConstant.OPENAPI_400_MSG, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationErrorResponse.class))}),
            @ApiResponse(responseCode = EmployeeConstant.HTTP_NOT_FOUND_CODE, description = EmployeeConstant.OPENAPI_404_MSG, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BusinessErrorResponse.class))})
    })
    @GetMapping("/employee/{empId}")
    public ResponseEntity<Employee> getEmployee(@Pattern(regexp = EmployeeConstant.EMP_ID_REGEXP, message = EmployeeConstant.EMP_ID_REGEXP_MSG)
                                                @PathVariable @Parameter(description = EmployeeConstant.OPENAPI_EMPID_MSG) String empId) {
        LOGGER.info("Execution started for fetching employee object...");
        Employee employee = employeeService.getEmployeeDetail(empId);
        LOGGER.info("Employee object fetched successfully: {}", employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @Operation(summary = EmployeeConstant.OPENAPI_POST_EMP_SUMMARY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = EmployeeConstant.HTTP_CREATED_CODE, description = EmployeeConstant.OPENAPI_POST_EMP_201_MSG, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = EmployeeConstant.HTTP_BAD_REQ_CODE, description = EmployeeConstant.OPENAPI_400_MSG, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationErrorResponse.class))})
    })
    @PostMapping("/employee")
    public ResponseEntity<Employee> addEmployee(@Valid @RequestBody Employee employee) {
        LOGGER.info("Execution started for creating employee object...");
        Employee createdEmployee = employeeService.createEmployee(employee);
        LOGGER.info("Employee object created successfully: {}", employee);
        return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
    }

    @Operation(summary = EmployeeConstant.OPENAPI_PUT_EMP_SUMMARY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = EmployeeConstant.HTTP_OK_CODE, description = EmployeeConstant.OPENAPI_PUT_EMP_200_MSG, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Employee.class))}),
            @ApiResponse(responseCode = EmployeeConstant.HTTP_BAD_REQ_CODE, description = EmployeeConstant.OPENAPI_400_MSG, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationErrorResponse.class))}),
            @ApiResponse(responseCode = EmployeeConstant.HTTP_NOT_FOUND_CODE, description = EmployeeConstant.OPENAPI_404_MSG, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BusinessErrorResponse.class))})
    })
    @PutMapping("/employee")
    public ResponseEntity<Employee> updateEmployee(@Valid @RequestBody Employee employee) {
        LOGGER.info("Execution started for updating employee object...");
        Employee updatedEmployee = employeeService.updateEmployee(employee);
        LOGGER.info("Employee object updated successfully: {}", updatedEmployee);
        return new ResponseEntity<>(updatedEmployee, HttpStatus.OK);
    }

    @Operation(summary = EmployeeConstant.OPENAPI_DELETE_EMP_SUMMARY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = EmployeeConstant.HTTP_OK_CODE, description = EmployeeConstant.OPENAPI_DELETE_EMP_200_MSG),
            @ApiResponse(responseCode = EmployeeConstant.HTTP_BAD_REQ_CODE, description = EmployeeConstant.OPENAPI_400_MSG, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ValidationErrorResponse.class))}),
            @ApiResponse(responseCode = EmployeeConstant.HTTP_NOT_FOUND_CODE, description = EmployeeConstant.OPENAPI_404_MSG, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BusinessErrorResponse.class))}),
            @ApiResponse(responseCode = EmployeeConstant.HTTP_INTERNAL_SERVER_ERR_CODE, description = EmployeeConstant.OPENAPI_DELETE_EMP_500_MSG, content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = BusinessErrorResponse.class))})
    })
    @DeleteMapping("/employee/{empId}")
    public ResponseEntity<Void> deleteEmployee(@Pattern(regexp = EmployeeConstant.EMP_ID_REGEXP, message = EmployeeConstant.EMP_ID_REGEXP_MSG)
                                                 @PathVariable @Parameter(description = EmployeeConstant.OPENAPI_EMPID_MSG) String empId) {
        LOGGER.info("Execution started for deleting employee object...");
        employeeService.deleteEmployee(empId);
        LOGGER.info("Employee object deleted successfully");
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
