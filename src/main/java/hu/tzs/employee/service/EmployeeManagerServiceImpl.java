package hu.tzs.employee.service;

import hu.tzs.employee.service.model.Employee;
import hu.tzs.employee.persist.EmployeeDao;
import hu.tzs.employee.service.exception.EmployeeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeManagerServiceImpl implements EmployeeManagerService {

    private final EmployeeDao employeeDao;

    @Override
    public Collection<Employee> getEmployees() {
        return employeeDao.getEmployees();
    }

    @Override
    public Collection<Employee> getEmployees(String firstName, String lastName, String title) {
        Collection<Employee> employees = employeeDao.getEmployees(firstName, lastName);
        if (title != null && !title.isBlank()) {
            return employees.stream().filter(e -> title.equals(e.getTitle())).collect(Collectors.toList());
        }
        return employees;
    }

    @Override
    public Employee getEmployee(int empNo) throws EmployeeNotFoundException {
        return employeeDao.getEmployee(empNo);
    }
}
