package hu.tzs.employee.persist;

import hu.tzs.employee.service.model.Employee;

import java.util.Collection;

public interface EmployeeDao {

    Collection<Employee> getEmployees();
}
