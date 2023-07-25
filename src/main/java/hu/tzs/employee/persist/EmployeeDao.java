package hu.tzs.employee.persist;

import hu.tzs.employee.service.exception.EmployeeNotFoundException;
import hu.tzs.employee.service.model.Employee;

import java.util.Collection;

public interface EmployeeDao {

    Collection<Employee> getEmployees();

    Employee getEmployee(int empNo) throws EmployeeNotFoundException;
}
