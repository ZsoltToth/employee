package hu.tzs.employee.service;

import hu.tzs.employee.controller.dto.EmployeeDto;
import hu.tzs.employee.model.Department;
import hu.tzs.employee.model.Employee;
import hu.tzs.employee.model.Gender;
import hu.tzs.employee.service.exception.EmployeeNotFoundException;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;


@Service
public class EmployeeManagerServiceImpl implements EmployeeManagerService {
    @Override
    public Collection<Employee> getEmployees() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Department department = new Department("kitchen", "Kitchen");
            Employee alice = new Employee(0,"Alice", "Doe", Gender.FEMALE, dateFormat.parse("2012-01-04"),"Cook", 10, department );
            Employee bob = new Employee(1,"Bob", "Smith", Gender.MALE, dateFormat.parse("2012-01-04"),"Waiter", 10, department );
            return List.of(alice, bob);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Employee getEmployee(int empNo) throws EmployeeNotFoundException {
        return null;
    }
}
