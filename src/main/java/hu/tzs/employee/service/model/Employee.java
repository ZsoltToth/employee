package hu.tzs.employee.service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor
@Getter
public class Employee {

    private int empNo;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Date hireDate;
    private String title;
    private int salary;
    private Department department;
}
