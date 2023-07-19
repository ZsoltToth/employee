package hu.tzs.employee.controller.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDto {

    private int empNo;

    private String firstName;

    private String lastName;

    private char gender;

    private String hireDate;

    private String title;

    private int salary;

    private String department;
}
