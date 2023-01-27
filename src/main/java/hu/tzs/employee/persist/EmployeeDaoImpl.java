package hu.tzs.employee.persist;

import hu.tzs.employee.persist.entity.EmployeeEntity;
import hu.tzs.employee.persist.entity.SalaryEntity;
import hu.tzs.employee.persist.entity.TitleEntity;
import hu.tzs.employee.persist.repository.EmployeeRepository;
import hu.tzs.employee.service.model.Employee;
import hu.tzs.employee.service.model.Gender;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
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
                getCurrentTitle(entity),
                getCurrentSalary(entity),
                null);
            return employee;
        }).collect(Collectors.toList());
    }

    private String getCurrentTitle(EmployeeEntity employee) {
        List<TitleEntity> titles =
            employee.getTitles().stream().filter(title -> title.getToDate().after(new Date())).collect(
                Collectors.toList());
        if (titles.size() == 1) {
            return titles.get(0).getTitle();
        }
        return "";
    }

    private int getCurrentSalary(EmployeeEntity employee) {
        List<SalaryEntity> salaries =
            employee.getSalaries().stream().filter(salary -> salary.getToDate().after(new Date())).collect(
                Collectors.toList());
        if (salaries.size() == 1) {
            return salaries.get(0).getSalary();
        }
        return -1;
    }
}
