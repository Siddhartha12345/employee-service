package com.employee.controller;

import com.employee.constant.EmployeeTestConstant;
import com.employee.entity.Department;
import com.employee.entity.Employee;
import com.employee.feign.DepartmentFeign;
import com.employee.service.IEmployeeService;
import com.employee.service.impl.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest(EmployeeController.class)
@Import(EmployeeService.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IEmployeeService employeeService;

    @MockBean
    private DepartmentFeign departmentFeign;

    @Test
    @DisplayName("Test to retrieve employee list successfully")
    public void testGetEmpList_Success() throws Exception {
        Department department = Department.builder().empId("E01").deptId("D01").deptName("HR").build();
        Mockito.when(departmentFeign.getDepartmentByEmpId(Mockito.anyString())).thenReturn(department);
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(EmployeeTestConstant.GET_EMP_LIST_ENDPOINT)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_OK_CODE);
    }

    @Test
    @DisplayName("Test success scenario to retrieve employee by ID")
    public void testEmployeeById_Success() throws Exception {
        Department department = Department.builder().empId("E01").deptId("D01").deptName("HR").build();
        Mockito.when(departmentFeign.getDepartmentByEmpId(Mockito.anyString())).thenReturn(department);
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(EmployeeTestConstant.GET_EMP_BY_ID_ENDPOINT, "E01")
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_OK_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to retrieve employee by passing a non existing ID")
    public void testEmployeeById_EmpNotFound() throws Exception {
        Department department = Department.builder().empId("E01").deptId("D01").deptName("HR").build();
        Mockito.when(departmentFeign.getDepartmentByEmpId(Mockito.anyString())).thenReturn(department);
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(EmployeeTestConstant.GET_EMP_BY_ID_ENDPOINT, "E04")
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_NOT_FOUND_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to retrieve employee by passing an invalid ID")
    public void testEmployeeById_InvalidEmpId() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(EmployeeTestConstant.GET_EMP_BY_ID_ENDPOINT, "A04")
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_BAD_REQUEST_CODE);
    }

    @Test
    @DisplayName("Test success scenario to create employee")
    public void testPost_ToCreateEmployee() throws Exception {
        Employee employee = Employee.builder().empId("E03").empName("Test Employee").build();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(EmployeeTestConstant.POST_EMP_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_CREATED_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to create employee by passing invalid Emp ID")
    public void testPost_InvalidEmpId() throws Exception {
        Employee employee = Employee.builder().empId("A03").empName("Test Employee").build();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(EmployeeTestConstant.POST_EMP_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_BAD_REQUEST_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to create employee by passing empty Emp name")
    public void testPost_EmptyEmpName() throws Exception {
        Employee employee = Employee.builder().empId("E03").build();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(EmployeeTestConstant.POST_EMP_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_BAD_REQUEST_CODE);
    }
}
