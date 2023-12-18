package hu.tzs.employee.persist;

import hu.tzs.employee.persist.entity.DepartmentEmployee;
import hu.tzs.employee.persist.entity.DepartmentEntity;
import hu.tzs.employee.persist.entity.EmployeeEntity;
import hu.tzs.employee.persist.entity.SalaryEntity;
import hu.tzs.employee.persist.entity.TitleEntity;
import hu.tzs.employee.persist.repository.EmployeeRepository;
import hu.tzs.employee.service.exception.EmployeeNotFoundException;
import hu.tzs.employee.service.model.Employee;
import hu.tzs.employee.service.model.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class EmployeeDaoImplTest {

    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeDaoImpl employeeDao;

    private static SimpleDateFormat dateFormat;

    static {
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }


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
        entity.setDepartments(List.of(
            DepartmentEmployee.builder().deptNo("01").empNo(1).fromDate(dateFormat.parse("1990-01-01"))
                .toDate(dateFormat.parse("9999-01-01")).department(new DepartmentEntity("01", "Department")).build()));
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
        assertThat(employee.getDepartment()).isNotNull();
    }

    @Test
    @DisplayName("Should return with employee without title if there is no Title Entity")
    void getEmployeesShouldReturnEmployeeWithoutTitleWhenThereIsNoTitleEntity() throws ParseException {
        // given
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

    @Test
    @DisplayName("Get Employee by empNo Happy Path")
    public void getEmployeeByIdShouldReturnEmployeeWhenEmployeeIdIsFound()
        throws ParseException, EmployeeNotFoundException {
        // given
        final int EMPNO_EXISTING = 1;
        EmployeeEntity entity = new EmployeeEntity();
        entity.setEmpNo(EMPNO_EXISTING);
        entity.setFirstName("Alice");
        entity.setLastName("Doe");
        entity.setGender('F');
        entity.setBirthDate(dateFormat.parse("1990-01-01"));
        entity.setHireDate(dateFormat.parse("2000-01-01"));
        when(repository.findById(EMPNO_EXISTING)).thenReturn(Optional.of(entity));
        // when
        Employee actual = employeeDao.getEmployee(EMPNO_EXISTING);
        // then
        assertThat(actual.getEmpNo()).isEqualTo(EMPNO_EXISTING);
        assertThat(actual.getFirstName()).isEqualTo("Alice");
        assertThat(actual.getLastName()).isEqualTo("Doe");
        assertThat(actual.getGender()).isEqualByComparingTo(Gender.FEMALE);
    }

    @Test
    @DisplayName("Get Employee by empNo throws EmployeeNotFoundException")
    public void getEmployeeByIdShouldThrowEmployeeNotFoundExceptionWhenEmpNoIsNotFound() {
        // given
        final int EMPNO_NOT_EXISTING = -1;
        doReturn(Optional.empty()).when(repository).findById(EMPNO_NOT_EXISTING);
        // when
        assertThatThrownBy(()-> {
            employeeDao.getEmployee(EMPNO_NOT_EXISTING);
        }).isInstanceOf(EmployeeNotFoundException.class);
        // then
    }

    @Test
    @DisplayName("Get Employee Based on FirstName and LastName")
    public void getEmployeeByFirstNameLastName(){
        // given
        final String FIRST_NAME = "John";
        final String LAST_NAME = "Doe";
        final int EXPECTED_RESULT_COUNT = 2;
        EmployeeEntity john1 = new EmployeeEntity();
        john1.setFirstName("John");
        john1.setLastName("Doe");
        john1.setGender('M');
        john1.setEmpNo(1);
        EmployeeEntity john2 = new EmployeeEntity();
        john2.setFirstName("John");
        john2.setLastName("Doe");
        john2.setGender('M');
        john2.setEmpNo(2);
        doReturn(new PageImpl<EmployeeEntity>(List.of(john1, john2)))
            .when(repository)
            .findAll(any(Example.class), any(PageRequest.class));
        // when
        Collection<Employee> actual = employeeDao.getEmployees(FIRST_NAME, LAST_NAME);
        // then
        assertThat(actual).hasSize(EXPECTED_RESULT_COUNT);
    }
}