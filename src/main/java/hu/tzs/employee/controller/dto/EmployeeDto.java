package hu.tzs.employee.controller.dto;

import hu.tzs.employee.model.Gender;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

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

//        department: {
//            depNo: string
//            department: string
}
