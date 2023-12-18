package hu.tzs.employee.persist;

import hu.tzs.employee.persist.entity.DepartmentEmployee;
import hu.tzs.employee.persist.entity.DepartmentEntity;
import hu.tzs.employee.persist.entity.EmployeeEntity;
import hu.tzs.employee.persist.entity.SalaryEntity;
import hu.tzs.employee.persist.entity.TitleEntity;
import hu.tzs.employee.persist.repository.EmployeeRepository;
import hu.tzs.employee.service.exception.EmployeeNotFoundException;
import hu.tzs.employee.service.model.Department;
import hu.tzs.employee.service.model.Employee;
import hu.tzs.employee.service.model.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeDaoImpl implements EmployeeDao {

    private final EmployeeRepository employeeRepository;

    @Override
    public Collection<Employee> getEmployees() {
        final int pageIndex = 0;
        final int pageSize = 20;
        return employeeRepository.findAll(PageRequest.of(pageIndex, pageSize)).stream()
            .map(this::mapEmployeeEntityToEmployee).collect(Collectors.toList());
    }

    @Override
    public Collection<Employee> getEmployees(String firstName, String lastName) {
        final int pageIndex = 0;
        final int pageSize = 20;
        EmployeeEntity.EmployeeEntityBuilder exampleEmployeeBuilder = EmployeeEntity.builder();
        exampleEmployeeBuilder.empNo(null);
        if (firstName != null && !firstName.isBlank()) {
            exampleEmployeeBuilder.firstName(firstName);
        }
        if (lastName != null && !lastName.isBlank()) {
            exampleEmployeeBuilder.lastName(lastName);
        }
        EmployeeEntity exampleEmployee = exampleEmployeeBuilder.build();
        return employeeRepository.findAll(Example.of(exampleEmployee), PageRequest.of(pageIndex, pageSize)).stream()
            .map(this::mapEmployeeEntityToEmployee).collect(
                Collectors.toList());
    }

    @Override
    public Employee getEmployee(int empNo) throws EmployeeNotFoundException {
        Optional<EmployeeEntity> entity = employeeRepository.findById(empNo);
        if (entity.isEmpty()) {
            throw new EmployeeNotFoundException(empNo, "Employee Not Found.", Optional.empty());
        }
        return mapEmployeeEntityToEmployee(entity.get());
    }

    private Employee mapEmployeeEntityToEmployee(EmployeeEntity entity) {
        Gender gender = null;
        if (entity.getGender() != null) {
            gender = entity.getGender() == 'M' ? Gender.MALE : Gender.FEMALE;
        }
        Employee employee = new Employee(
            entity.getEmpNo(),
            entity.getFirstName(),
            entity.getLastName(),
            gender,
            entity.getHireDate(),
            getCurrentTitle(entity),
            getCurrentSalary(entity),
            getCurrentDepartment(entity));
        return employee;
    }

    private String getCurrentTitle(EmployeeEntity employee) {
        if (employee.getTitles() == null) {
            return "";
        }
        List<TitleEntity> titles =
            employee.getTitles().stream().filter(title -> title.getToDate().after(new Date())).collect(
                Collectors.toList());
        if (titles.size() == 1) {
            return titles.get(0).getTitle();
        }
        return "";
    }

    private int getCurrentSalary(EmployeeEntity employee) {
        if (employee.getSalaries() == null) {
            return -1;
        }
        List<SalaryEntity> salaries =
            employee.getSalaries().stream().filter(salary -> salary.getToDate().after(new Date())).collect(
                Collectors.toList());
        if (salaries.size() == 1) {
            return salaries.get(0).getSalary();
        }
        return -1;
    }

    private Department getCurrentDepartment(EmployeeEntity employee) {
        if (employee.getDepartments() == null || employee.getDepartments().isEmpty()) {
            return null;
        }
        List<DepartmentEntity> departmentEntities = employee.getDepartments()
            .stream()
            .filter(department -> department.getToDate().after(new Date()))
            .map(DepartmentEmployee::getDepartment)
            .collect(Collectors.toList());
        if (departmentEntities.isEmpty()) {
            return null;
        }
        DepartmentEntity currentDepartmentEntity = departmentEntities.get(0);
        return new Department(currentDepartmentEntity.getDepartmentNo(), currentDepartmentEntity.getName());
    }

}
