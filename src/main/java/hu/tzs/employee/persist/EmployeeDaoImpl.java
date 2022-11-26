package hu.tzs.employee.persist;

import hu.tzs.employee.service.model.Department;
import hu.tzs.employee.service.model.Employee;
import hu.tzs.employee.service.model.Gender;
import hu.tzs.employee.persist.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeDaoImpl implements EmployeeDao {

    private final EmployeeRepository employeeRepository;

    @Override
    public Collection<Employee> getEmployees() {
        final int pageIndex = 0;
        final int pageSize = 20;
        return employeeRepository.findAll(PageRequest.of(pageIndex, pageSize)).stream().map(entity -> {
            Employee employee = new Employee(
                entity.getEmpNo(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getGender() == 'M' ? Gender.MALE : Gender.FEMALE,
                entity.getHireDate(),
                "",
                0,
                null);
            return employee;
        }).collect(Collectors.toList());
    }
}
