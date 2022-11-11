package hu.tzs.employee.controller;

import hu.tzs.employee.controller.dto.EmployeeDto;
import hu.tzs.employee.service.EmployeeManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeManagerService employeeManagerService;

    @GetMapping("")
    public Collection<EmployeeDto> fetchAll() {
        return employeeManagerService.getEmployees().stream().map(employee ->
            EmployeeDto.builder()
                .empNo(employee.getEmpNo())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .gender(employee.getGender().getValue())
                .hireDate(employee.getHireDate().toString())
                .salary(employee.getSalary())
                .title(employee.getTitle())
                .build()
        ).collect(Collectors.toList());
    }
}