package com.employee.controller;

import com.employee.constant.EmployeeTestConstant;
import com.employee.entity.BasicDetail;
import com.employee.entity.Employee;
import com.employee.feign.DepartmentFeign;
import com.employee.repository.EmployeeGarbageRepository;
import com.employee.repository.EmployeeJDBCRepository;
import com.employee.repository.EmployeeJPARepository;
import com.employee.response.BusinessErrorResponse;
import com.employee.response.ValidationError;
import com.employee.response.ValidationErrorResponse;
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
    private EmployeeJPARepository employeeJPARepository;

    @MockBean
    private EmployeeGarbageRepository employeeGarbageRepository;

    @MockBean
    private EmployeeJDBCRepository employeeJDBCRepository;


    // GET /employee ------------------------------------------
    @Test
    @DisplayName("Test to retrieve employee list successfully")
    public void testGetEmpList_Success() throws Exception {
        BasicDetail basicDetail = BasicDetail.builder().employeeId("EID1").firstName("Vikas").lastName("Sharma").role("Role-A").image("http://sample-img.png").build();
        Mockito.when(employeeJDBCRepository.getEmployeeBasicDetails()).thenReturn(Arrays.asList(basicDetail));
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(EmployeeTestConstant.GET_EMP_LIST_ENDPOINT)
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_OK_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to retrieve employee list")
    public void testGetEmpList_Failure() throws Exception {
        Mockito.when(employeeJPARepository.findAll()).thenReturn(Arrays.asList());
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(EmployeeTestConstant.GET_EMP_LIST_ENDPOINT)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn();
        BusinessErrorResponse errorResponse = objectMapper.readValue(response.getResponse().getContentAsString(), BusinessErrorResponse.class);
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_NOT_FOUND_CODE);
        Assertions.assertEquals(errorResponse.getErrorCode(), EmployeeTestConstant.EMPTY_LIST_ERR_CODE);
    }

    // GET /employee/{empId} ---------------------------------
    @Test
    @DisplayName("Test success scenario to retrieve employee by ID")
    public void testEmployeeById_Success() throws Exception {
        Employee employee = Employee.builder().employeeId("EID1").firstName("Vikas").lastName("Sharma").address("Test Address")
                .emailId("vik.sh@abc.com").image("http://abc.png").mobileNo("1111122222").maritalStatus("Single").build();
        Mockito.when(employeeJPARepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(employee));
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(EmployeeTestConstant.GET_EMP_BY_ID_ENDPOINT, "EID1")
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_OK_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to retrieve employee by passing a non existing ID")
    public void testEmployeeById_EmpNotFound() throws Exception {
        Mockito.when(employeeJPARepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(EmployeeTestConstant.GET_EMP_BY_ID_ENDPOINT, "EID4")
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
        BusinessErrorResponse errorResponse = objectMapper.readValue(response.getResponse().getContentAsString(), BusinessErrorResponse.class);
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_NOT_FOUND_CODE);
        Assertions.assertEquals(errorResponse.getErrorCode(), EmployeeTestConstant.EMP_NOT_FOUND_ERR_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to retrieve employee by passing an invalid ID")
    public void testEmployeeById_InvalidEmpId() throws Exception {
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.get(EmployeeTestConstant.GET_EMP_BY_ID_ENDPOINT, "A04")
                        .accept(MediaType.APPLICATION_JSON))
                        .andReturn();
        ValidationErrorResponse validationErrorResponse = objectMapper.readValue(response.getResponse().getContentAsString(), ValidationErrorResponse.class);
        ValidationError validationError = validationErrorResponse.getValidationErrors().get(0);
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_BAD_REQUEST_CODE);
        Assertions.assertEquals(validationError.getErrorCode(), EmployeeTestConstant.VALIDATION_ERR_CODE);
    }

    // POST /employee ------------------------------------
    @Test
    @DisplayName("Test success scenario to create employee")
    public void testPost_ToCreateEmployee() throws Exception {
        Employee employee = Employee.builder().employeeId("EID1").firstName("Vikas").lastName("Sharma").role("UI Dev")
                .designation("Software Engg").gender("Male").salary(1000000).address("Test Address")
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
        ValidationErrorResponse validationErrorResponse = objectMapper.readValue(response.getResponse().getContentAsString(), ValidationErrorResponse.class);
        ValidationError validationError = validationErrorResponse.getValidationErrors().get(0);
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_BAD_REQUEST_CODE);
        Assertions.assertEquals(validationError.getErrorCode(), EmployeeTestConstant.VALIDATION_ERR_CODE);
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
        ValidationErrorResponse validationErrorResponse = objectMapper.readValue(response.getResponse().getContentAsString(), ValidationErrorResponse.class);
        ValidationError validationError = validationErrorResponse.getValidationErrors().get(0);
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_BAD_REQUEST_CODE);
        Assertions.assertEquals(validationError.getErrorCode(), EmployeeTestConstant.VALIDATION_ERR_CODE);
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
        ValidationErrorResponse validationErrorResponse = objectMapper.readValue(response.getResponse().getContentAsString(), ValidationErrorResponse.class);
        ValidationError validationError = validationErrorResponse.getValidationErrors().get(0);
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_BAD_REQUEST_CODE);
        Assertions.assertEquals(validationError.getErrorCode(), EmployeeTestConstant.VALIDATION_ERR_CODE);
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
        ValidationErrorResponse validationErrorResponse = objectMapper.readValue(response.getResponse().getContentAsString(), ValidationErrorResponse.class);
        ValidationError validationError = validationErrorResponse.getValidationErrors().get(0);
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_BAD_REQUEST_CODE);
        Assertions.assertEquals(validationError.getErrorCode(), EmployeeTestConstant.VALIDATION_ERR_CODE);
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
        ValidationErrorResponse validationErrorResponse = objectMapper.readValue(response.getResponse().getContentAsString(), ValidationErrorResponse.class);
        ValidationError validationError = validationErrorResponse.getValidationErrors().get(0);
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_BAD_REQUEST_CODE);
        Assertions.assertEquals(validationError.getErrorCode(), EmployeeTestConstant.VALIDATION_ERR_CODE);
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
        ValidationErrorResponse validationErrorResponse = objectMapper.readValue(response.getResponse().getContentAsString(), ValidationErrorResponse.class);
        ValidationError validationError = validationErrorResponse.getValidationErrors().get(0);
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_BAD_REQUEST_CODE);
        Assertions.assertEquals(validationError.getErrorCode(), EmployeeTestConstant.VALIDATION_ERR_CODE);
    }

    // PUT /employee ---------------------------------------
    @Test
    @DisplayName("Test success scenario to update employee")
    public void testPut_ToUpdateEmployee() throws Exception {
        Employee employee = Employee.builder().employeeId("EID1").firstName("Vikas").lastName("Sharma").role("UI Dev")
                .designation("Software Engg").gender("Male").salary(1000000).address("Test Address")
                .emailId("vik.sh@abc.com").image("http://abc.png").mobileNo("1111122222").maritalStatus("Single").build();
        Mockito.when(employeeJPARepository.findById(Mockito.anyString())).thenReturn(Optional.of(employee));
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put(EmployeeTestConstant.PUT_EMP_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_OK_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to update employee when employee id does not exist")
    public void testPut_InvalidEmpId() throws Exception {
        Employee employee = Employee.builder().employeeId("EID1").firstName("Vikas").lastName("Sharma").role("UI Dev")
                .designation("Software Engg").gender("Male").salary(1000000).address("Test Address")
                .emailId("vik.sh@abc.com").image("http://abc.png").mobileNo("1111122222").maritalStatus("Single").build();
        Mockito.when(employeeJPARepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.put(EmployeeTestConstant.PUT_EMP_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andReturn();
        BusinessErrorResponse errorResponse = objectMapper.readValue(response.getResponse().getContentAsString(), BusinessErrorResponse.class);
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_NOT_FOUND_CODE);
        Assertions.assertEquals(errorResponse.getErrorCode(), EmployeeTestConstant.EMP_NOT_FOUND_ERR_CODE);
    }

    // DELETE /employee/{empid} ----------------------------------------------
    @Test
    @DisplayName("Test success scenario to delete employee")
    public void testDelete_Success() throws Exception {
        Employee employee = Employee.builder().employeeId("EID1").firstName("Vikas").lastName("Sharma").address("Test Address")
                .emailId("vik.sh@abc.com").image("http://abc.png").mobileNo("1111122222").maritalStatus("Single").build();
        Mockito.when(employeeJPARepository.findById(Mockito.anyString())).thenReturn(Optional.of(employee));
        Mockito.when(employeeGarbageRepository.saveEmpIdInGarbageTable(Mockito.anyString())).thenReturn(1);
        Mockito.doNothing().when(employeeJPARepository).deleteById(Mockito.anyString());
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.delete(EmployeeTestConstant.DELETE_EMP_ENDPOINT, "EID1"))
                                    .andReturn();
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_OK_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to delete employee when Emp ID does not exist")
    public void testDelete_InvalidEmpId() throws Exception {
        Mockito.when(employeeJPARepository.findById(Mockito.anyString())).thenReturn(Optional.empty());
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.delete(EmployeeTestConstant.DELETE_EMP_ENDPOINT, "EID1"))
                .andReturn();
        BusinessErrorResponse errorResponse = objectMapper.readValue(response.getResponse().getContentAsString(), BusinessErrorResponse.class);
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_NOT_FOUND_CODE);
        Assertions.assertEquals(errorResponse.getErrorCode(), EmployeeTestConstant.EMP_NOT_FOUND_ERR_CODE);
    }

    @Test
    @DisplayName("Test negative scenario to delete employee when Emp Id could not be saved in garbage table")
    public void testDelete_EmpIdSaveError() throws Exception {
        Employee employee = Employee.builder().employeeId("EID1").firstName("Vikas").lastName("Sharma").address("Test Address")
                .emailId("vik.sh@abc.com").image("http://abc.png").mobileNo("1111122222").maritalStatus("Single").build();
        Mockito.when(employeeJPARepository.findById(Mockito.anyString())).thenReturn(Optional.of(employee));
        Mockito.when(employeeGarbageRepository.saveEmpIdInGarbageTable(Mockito.anyString())).thenReturn(0);
        MvcResult response = mockMvc.perform(MockMvcRequestBuilders.delete(EmployeeTestConstant.DELETE_EMP_ENDPOINT, "EID1"))
                .andReturn();
        BusinessErrorResponse errorResponse = objectMapper.readValue(response.getResponse().getContentAsString(), BusinessErrorResponse.class);
        Assertions.assertEquals(response.getResponse().getStatus(), EmployeeTestConstant.HTTP_INTERNAL_SERVER_ERR_CODE);
        Assertions.assertEquals(errorResponse.getErrorCode(), EmployeeTestConstant.GARBAGE_TBL_PERSISTENCE_ERR_CODE);
    }
}
