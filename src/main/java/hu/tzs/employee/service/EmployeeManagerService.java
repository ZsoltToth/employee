package hu.tzs.employee.service;

import hu.tzs.employee.model.Employee;
import hu.tzs.employee.service.exception.EmployeeNotFoundException;

import java.util.Collection;

public interface EmployeeManagerService {

    Collection<Employee> getEmployees();

    Employee getEmployee(int empNo) throws EmployeeNotFoundException;
}
