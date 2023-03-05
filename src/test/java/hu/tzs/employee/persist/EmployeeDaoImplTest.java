package hu.tzs.employee.persist;

import hu.tzs.employee.persist.entity.EmployeeEntity;
import hu.tzs.employee.persist.entity.SalaryEntity;
import hu.tzs.employee.persist.entity.TitleEntity;
import hu.tzs.employee.persist.repository.EmployeeRepository;
import hu.tzs.employee.service.model.Employee;
import hu.tzs.employee.service.model.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class EmployeeDaoImplTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeDaoImpl employeeDao;


    @Test
    @DisplayName("Should return with empty collection if the database is empty")
    void getEmployeesShouldReturnEmptyCollectionWhenTheRepositoryIsEmpty() {
        // given
        Page<EmployeeEntity> repositoryResponse = new PageImpl<>(List.of());
        when(repository.findAll(any(PageRequest.class))).thenReturn(repositoryResponse);
        // when
        Collection<Employee> actual = employeeDao.getEmployees();
        // then
        assertThat(actual).isEmpty();
    }

    @Test
    @DisplayName("Should return with employee if all fields are known")
    void getEmployeesShouldReturnEmployeeWhenAllDataIsKnown() throws ParseException {
        // given
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SalaryEntity salary = new SalaryEntity();
        salary.setSalary(100);
        salary.setEmpNo(1);
        salary.setFromDate(dateFormat.parse("2000-01-01"));
        salary.setToDate(dateFormat.parse("9999-01-01"));
        TitleEntity title = new TitleEntity();
        title.setTitle("Staff");
        title.setEmpNo(1);
        title.setFromDate(dateFormat.parse("2000-01-01"));
        title.setToDate(dateFormat.parse("9999-01-01"));
        EmployeeEntity entity = new EmployeeEntity();
        entity.setEmpNo(1);
        entity.setFirstName("Alice");
        entity.setLastName("Doe");
        entity.setGender('F');
        entity.setBirthDate(dateFormat.parse("1990-01-01"));
        entity.setHireDate(dateFormat.parse("2000-01-01"));
        entity.setSalaries(List.of(salary));
        entity.setTitles(List.of(title));
        Page<EmployeeEntity> repositoryResponse = new PageImpl<>(List.of(entity));
        when(repository.findAll(any(PageRequest.class))).thenReturn(repositoryResponse);
        // when
        Collection<Employee> actual = employeeDao.getEmployees();
        // then
        assertThat(actual).hasSize(1);
        Employee employee = ((List<Employee>) actual).get(0);
        assertThat(employee.getEmpNo()).isEqualTo(1);
        assertThat(employee.getFirstName()).isEqualTo("Alice");
        assertThat(employee.getLastName()).isEqualTo("Doe");
        assertThat(employee.getGender()).isEqualByComparingTo(Gender.FEMALE);
        assertThat(employee.getSalary()).isEqualTo(100);
        assertThat(employee.getTitle()).isEqualTo("Staff");
    }

    @Test
    @DisplayName("Should return with employee without title if there is no Title Entity")
    void getEmployeesShouldReturnEmployeeWithoutTitleWhenThereIsNoTitleEntity() throws ParseException {
        // given
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SalaryEntity salary = new SalaryEntity();
        salary.setSalary(100);
        salary.setEmpNo(1);
        salary.setFromDate(dateFormat.parse("2000-01-01"));
        salary.setToDate(dateFormat.parse("9999-01-01"));
        EmployeeEntity entity = new EmployeeEntity();
        entity.setEmpNo(1);
        entity.setFirstName("Alice");
        entity.setLastName("Doe");
        entity.setGender('F');
        entity.setBirthDate(dateFormat.parse("1990-01-01"));
        entity.setHireDate(dateFormat.parse("2000-01-01"));
        entity.setSalaries(List.of(salary));
        Page<EmployeeEntity> repositoryResponse = new PageImpl<>(List.of(entity));
        when(repository.findAll(any(PageRequest.class))).thenReturn(repositoryResponse);
        // when
        Collection<Employee> actual = employeeDao.getEmployees();
        // then
        assertThat(actual).hasSize(1);
        Employee employee = ((List<Employee>) actual).get(0);
        assertThat(employee.getEmpNo()).isEqualTo(1);
        assertThat(employee.getFirstName()).isEqualTo("Alice");
        assertThat(employee.getLastName()).isEqualTo("Doe");
        assertThat(employee.getGender()).isEqualByComparingTo(Gender.FEMALE);
        assertThat(employee.getSalary()).isEqualTo(100);
        assertThat(employee.getTitle()).isEqualTo("");
    }

    @Test
    @DisplayName("Should return with employee without title if Title Entity has expired")
    void getEmployeesShouldReturnEmployeeWithoutTitleWhenTitleEntityHasExpired() throws ParseException {
        // given
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TitleEntity title = new TitleEntity();
        title.setTitle("Staff");
        title.setEmpNo(1);
        title.setFromDate(dateFormat.parse("2000-01-01"));
        title.setToDate(dateFormat.parse("2000-01-01"));
        SalaryEntity salary = new SalaryEntity();
        salary.setSalary(100);
        salary.setEmpNo(1);
        salary.setFromDate(dateFormat.parse("2000-01-01"));
        salary.setToDate(dateFormat.parse("9999-01-01"));
        EmployeeEntity entity = new EmployeeEntity();
        entity.setEmpNo(1);
        entity.setFirstName("Alice");
        entity.setLastName("Doe");
        entity.setGender('F');
        entity.setBirthDate(dateFormat.parse("1990-01-01"));
        entity.setHireDate(dateFormat.parse("2000-01-01"));
        entity.setSalaries(List.of(salary));
        entity.setTitles(List.of(title));
        Page<EmployeeEntity> repositoryResponse = new PageImpl<>(List.of(entity));
        when(repository.findAll(any(PageRequest.class))).thenReturn(repositoryResponse);
        // when
        Collection<Employee> actual = employeeDao.getEmployees();
        // then
        assertThat(actual).hasSize(1);
        Employee employee = ((List<Employee>) actual).get(0);
        assertThat(employee.getEmpNo()).isEqualTo(1);
        assertThat(employee.getFirstName()).isEqualTo("Alice");
        assertThat(employee.getLastName()).isEqualTo("Doe");
        assertThat(employee.getGender()).isEqualByComparingTo(Gender.FEMALE);
        assertThat(employee.getSalary()).isEqualTo(100);
        assertThat(employee.getTitle()).isEqualTo("");
    }

    @Test
    @DisplayName("Should return with employee with -1 salary if there is no Salary Entity")
    void getEmployeesShouldReturnEmployeeWithNegativeSalaryWhenThereIsNoSalaryEntity() throws ParseException {
        // given
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TitleEntity title = new TitleEntity();
        title.setTitle("Staff");
        title.setEmpNo(1);
        title.setFromDate(dateFormat.parse("2000-01-01"));
        title.setToDate(dateFormat.parse("9999-01-01"));
        EmployeeEntity entity = new EmployeeEntity();
        entity.setEmpNo(1);
        entity.setFirstName("Alice");
        entity.setLastName("Doe");
        entity.setGender('F');
        entity.setBirthDate(dateFormat.parse("1990-01-01"));
        entity.setHireDate(dateFormat.parse("2000-01-01"));
        entity.setTitles(List.of(title));
        Page<EmployeeEntity> repositoryResponse = new PageImpl<>(List.of(entity));
        when(repository.findAll(any(PageRequest.class))).thenReturn(repositoryResponse);
        // when
        Collection<Employee> actual = employeeDao.getEmployees();
        // then
        assertThat(actual).hasSize(1);
        Employee employee = ((List<Employee>) actual).get(0);
        assertThat(employee.getEmpNo()).isEqualTo(1);
        assertThat(employee.getFirstName()).isEqualTo("Alice");
        assertThat(employee.getLastName()).isEqualTo("Doe");
        assertThat(employee.getGender()).isEqualByComparingTo(Gender.FEMALE);
        assertThat(employee.getSalary()).isEqualTo(-1);
        assertThat(employee.getTitle()).isEqualTo("Staff");
    }

    @Test
    @DisplayName("Should return with employee with -1 salary if Salary Entity has expired")
    void getEmployeesShouldReturnEmployeeWithNegativeSalaryWhenSalaryEntityHasExpired() throws ParseException {
        // given
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        TitleEntity title = new TitleEntity();
        title.setTitle("Staff");
        title.setEmpNo(1);
        title.setFromDate(dateFormat.parse("2000-01-01"));
        title.setToDate(dateFormat.parse("9999-01-01"));
        SalaryEntity salary = new SalaryEntity();
        salary.setSalary(100);
        salary.setEmpNo(1);
        salary.setFromDate(dateFormat.parse("2000-01-01"));
        salary.setToDate(dateFormat.parse("2000-01-01"));
        EmployeeEntity entity = new EmployeeEntity();
        entity.setEmpNo(1);
        entity.setFirstName("Alice");
        entity.setLastName("Doe");
        entity.setGender('F');
        entity.setBirthDate(dateFormat.parse("1990-01-01"));
        entity.setHireDate(dateFormat.parse("2000-01-01"));
        entity.setSalaries(List.of(salary));
        entity.setTitles(List.of(title));
        Page<EmployeeEntity> repositoryResponse = new PageImpl<>(List.of(entity));
        when(repository.findAll(any(PageRequest.class))).thenReturn(repositoryResponse);
        // when
        Collection<Employee> actual = employeeDao.getEmployees();
        // then
        assertThat(actual).hasSize(1);
        Employee employee = ((List<Employee>) actual).get(0);
        assertThat(employee.getEmpNo()).isEqualTo(1);
        assertThat(employee.getFirstName()).isEqualTo("Alice");
        assertThat(employee.getLastName()).isEqualTo("Doe");
        assertThat(employee.getGender()).isEqualByComparingTo(Gender.FEMALE);
        assertThat(employee.getSalary()).isEqualTo(-1);
        assertThat(employee.getTitle()).isEqualTo("Staff");
    }
}