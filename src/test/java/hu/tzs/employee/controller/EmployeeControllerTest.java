package hu.tzs.employee.controller;

import hu.tzs.employee.service.EmployeeManagerService;
import hu.tzs.employee.service.model.Department;
import hu.tzs.employee.service.model.Employee;
import hu.tzs.employee.service.model.Gender;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeManagerService service;

    @InjectMocks
    private EmployeeController controller;

    @Test
    @DisplayName("Empty Employee Collection")
    public void shouldReturnEmptyArrayWhenThereAreNoEmployee() throws Exception {
        // given
        when(service.getEmployees()).thenReturn(Collections.emptyList());
        // when
        mockMvc.perform(get("/api/employees"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json("[]"));
        // then
        assertNotNull(controller);

    }

    @Test
    @DisplayName("Signle Employee in Collection")
    public void shouldReturnOneEmployeeArrayWhenThereIsOneEmployee() throws Exception {
        // given
        when(service.getEmployees()).thenReturn(List.of(TestDataProvider.getEmployee()));
        // when
        mockMvc.perform(get("/api/employees"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$", IsCollectionWithSize.hasSize(1)));
        // then
        assertNotNull(controller);

    }

    @Test
    @DisplayName("Get Employee by Id")
    public void shouldReturnEmployeeWhenEmployeeNoIsValidAndEmployeeExists() throws Exception{
        // given
        final int EMPLOYEE_NO = 1234;
        when(service.getEmployee(EMPLOYEE_NO)).thenReturn(TestDataProvider.getEmployee());
        // when
        mockMvc.perform(get(String.format("/api/employees/%d",EMPLOYEE_NO)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        // then
    }


    private static class TestDataProvider {

        public static Employee getEmployee() {

            int empNo = 123;
            String firstName = "Jhon";
            String lastName = "Doe";
            Gender gender = Gender.MALE;
            GregorianCalendar calender = new GregorianCalendar(1990, 01, 01);
            Date hireDate = Date.from(calender.toZonedDateTime().toInstant());
            String title = "Senior Engineer";
            int salary = 80500;

            Department department = null;
            return new Employee(empNo, firstName, lastName, gender, hireDate, title, salary, department);
        }
    }

}