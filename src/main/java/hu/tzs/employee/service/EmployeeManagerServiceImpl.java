package hu.tzs.employee.service;

import hu.tzs.employee.service.model.Employee;
import hu.tzs.employee.persist.EmployeeDao;
import hu.tzs.employee.service.exception.EmployeeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class EmployeeManagerServiceImpl implements EmployeeManagerService {

    private final EmployeeDao employeeDao;

    @Override
    public Collection<Employee> getEmployees() {
        return employeeDao.getEmployees();
    }

    @Override
    public Employee getEmployee(int empNo) throws EmployeeNotFoundException {
        return employeeDao.getEmployee(empNo);
    }
}
