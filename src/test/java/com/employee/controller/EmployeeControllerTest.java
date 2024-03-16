package com.employee.controller;

import com.employee.constant.EmployeeTestConstant;
import com.employee.entity.Employee;
import com.employee.feign.DepartmentFeign;
import com.employee.repository.EmployeeRepository;
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

import java.util.Arrays;
import java.util.Optional;

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

    @MockBean
    private EmployeeRepository employeeRepository;

    // GET /employee
    @Test
    @DisplayName("Test to retrieve employee list successfully")
    public void testGetEmpList_Success() throws Exception {
        Employee employee = Employee.builder().employeeId("EID1").firstName("Vikas").lastName("Sharma").address("Test Address")
                        .emailId("vik.sh@abc.com").image("http://abc.png").mobileNo("1111122222").maritalStatus("Single").build();
        Mockito.when(employeeRepository.findAll()).thenReturn(Arrays.asList(employee));
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(EmployeeTestConstant.GET_EMP_LIST_ENDPOINT)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_OK_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to retrieve employee list")
    public void testGetEmpList_Failure() throws Exception {
        Mockito.when(employeeRepository.findAll()).thenReturn(Arrays.asList());
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(EmployeeTestConstant.GET_EMP_LIST_ENDPOINT)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_NOT_FOUND_CODE);
    }

    // GET /employee/{empId}
    @Test
    @DisplayName("Test success scenario to retrieve employee by ID")
    public void testEmployeeById_Success() throws Exception {
        Employee employee = Employee.builder().employeeId("EID1").firstName("Vikas").lastName("Sharma").address("Test Address")
                .emailId("vik.sh@abc.com").image("http://abc.png").mobileNo("1111122222").maritalStatus("Single").build();
        Mockito.when(employeeRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(employee));
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(EmployeeTestConstant.GET_EMP_BY_ID_ENDPOINT, "EID1")
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_OK_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to retrieve employee by passing a non existing ID")
    public void testEmployeeById_EmpNotFound() throws Exception {
        Mockito.when(employeeRepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(EmployeeTestConstant.GET_EMP_BY_ID_ENDPOINT, "EID4")
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

    // POST /employee
    @Test
    @DisplayName("Test success scenario to create employee")
    public void testPost_ToCreateEmployee() throws Exception {
        Employee employee = Employee.builder().employeeId("EID1").firstName("Vikas").lastName("Sharma").address("Test Address")
                .emailId("vik.sh@abc.com").image("http://abc.png").mobileNo("1111122222").maritalStatus("Single").build();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(EmployeeTestConstant.POST_EMP_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_CREATED_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to create employee by passing invalid address format")
    public void testPost_InvalidAddress() throws Exception {
        Employee employee = Employee.builder().employeeId("EID1").firstName("Vikas").lastName("Sharma").address("Test")
                .emailId("vik.sh@abc.com").image("http://abc.png").mobileNo("1111122222").maritalStatus("Single").build();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(EmployeeTestConstant.POST_EMP_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_BAD_REQUEST_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to create employee by passing empty firstname")
    public void testPost_EmptyEmpName() throws Exception {
        Employee employee = Employee.builder().employeeId("EID1").lastName("Sharma").address("Test Address")
                .emailId("vik.sh@abc.com").image("http://abc.png").mobileNo("1111122222").maritalStatus("Single").build();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(EmployeeTestConstant.POST_EMP_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_BAD_REQUEST_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to create employee by passing invalid email")
    public void testPost_InvalidEmail() throws Exception {
        Employee employee = Employee.builder().employeeId("EID1").firstName("Vikas").lastName("Sharma").address("Test Address")
                .emailId("vik.sh").image("http://abc.png").mobileNo("1111122222").maritalStatus("Single").build();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(EmployeeTestConstant.POST_EMP_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_BAD_REQUEST_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to create employee by passing invalid image format")
    public void testPost_InvalidImage() throws Exception {
        Employee employee = Employee.builder().employeeId("EID1").firstName("Vikas").lastName("Sharma").address("Test Address")
                .emailId("vik.sh@abc.com").image("test").mobileNo("1111122222").maritalStatus("Single").build();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(EmployeeTestConstant.POST_EMP_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_BAD_REQUEST_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to create employee by passing invalid mobile number")
    public void testPost_InvalidMobile() throws Exception {
        Employee employee = Employee.builder().employeeId("EID1").firstName("Vikas").lastName("Sharma").address("Test Address")
                .emailId("vik.sh@abc.com").image("http://abc.png").mobileNo("111112222222233").maritalStatus("Single").build();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(EmployeeTestConstant.POST_EMP_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_BAD_REQUEST_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to create employee by passing invalid marital status")
    public void testPost_InvalidStatus() throws Exception {
        Employee employee = Employee.builder().employeeId("EID1").firstName("Vikas").lastName("Sharma").address("Test Address")
                .emailId("vik.sh@abc.com").image("http://abc.png").mobileNo("1111122222").maritalStatus("test").build();
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.post(EmployeeTestConstant.POST_EMP_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_BAD_REQUEST_CODE);
    }
}
