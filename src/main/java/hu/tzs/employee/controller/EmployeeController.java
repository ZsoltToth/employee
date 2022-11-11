package hu.tzs.employee.controller;

import hu.tzs.employee.controller.dto.EmployeeDto;
import hu.tzs.employee.model.Gender;
import hu.tzs.employee.service.EmployeeManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

//    private final EmployeeManagerService employeeManagerService;

    @GetMapping("")
    public Collection<EmployeeDto> fetchAll(){
        EmployeeDto alice = new EmployeeDto();
        alice.setEmpNo(0);
        alice.setFirstName("Alice");
        alice.setLastName("Doe");
        alice.setGender(Gender.FEMALE.getValue());
        alice.setHireDate("2000-01-02");
        alice.setSalary(0);
        alice.setTitle("Test Object");
        EmployeeDto bob = new EmployeeDto();
        bob.setEmpNo(0);
        bob.setFirstName("Bob");
        bob.setLastName("Smith");
        bob.setGender(Gender.MALE.getValue());
        bob.setHireDate("2010-12-02");
        bob.setSalary(0);
        bob.setTitle("Test Object");
       return List.of(
           alice, bob
       );
    }
}
