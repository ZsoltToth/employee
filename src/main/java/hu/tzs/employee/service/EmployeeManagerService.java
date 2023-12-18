package hu.tzs.employee.service;

import hu.tzs.employee.service.model.Employee;
import hu.tzs.employee.service.exception.EmployeeNotFoundException;

import java.util.Collection;

public interface EmployeeManagerService {

    Collection<Employee> getEmployees();

    Collection<Employee> getEmployees(String firstName, String lastName, String title);

    Employee getEmployee(int empNo) throws EmployeeNotFoundException;
}
