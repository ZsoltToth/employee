package hu.tzs.employee.controller;

import hu.tzs.employee.controller.dto.EmployeeDto;
import hu.tzs.employee.service.EmployeeManagerService;
import hu.tzs.employee.service.exception.EmployeeNotFoundException;
import hu.tzs.employee.service.model.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeManagerService employeeManagerService;

    @GetMapping("")
    public Collection<EmployeeDto> fetchAll(
        @RequestParam(required = false) String firstName,
        @RequestParam(required = false) String lastName,
        @RequestParam(required = false) String title
    ) {
        Collection<Employee> results = Collections.emptyList();
        if (firstName == null && lastName == null && title == null) {
            results = employeeManagerService.getEmployees();
        } else {
            results = employeeManagerService.getEmployees(firstName, lastName, title);
        }
        return results.stream()
            .map(this::mapEmployeeToEmployeeDto).collect(
                Collectors.toList());
    }

    @GetMapping("/{empNo}")
    public EmployeeDto fetchEmployee(@PathVariable int empNo) {
        try {
            return mapEmployeeToEmployeeDto(employeeManagerService.getEmployee(empNo));
        } catch (EmployeeNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    private EmployeeDto mapEmployeeToEmployeeDto(Employee employee) {
        return EmployeeDto.builder()
            .empNo(employee.getEmpNo())
            .firstName(employee.getFirstName())
            .lastName(employee.getLastName())
            .gender(employee.getGender().getValue())
            .hireDate(employee.getHireDate().toString())
            .salary(employee.getSalary())
            .title(employee.getTitle())
            .department(employee.getDepartment() != null ? employee.getDepartment().getName() : "")
            .build();
    }
}