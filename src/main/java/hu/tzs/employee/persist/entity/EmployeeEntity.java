package hu.tzs.employee.persist.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collection;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "employees")
@Table(name = "employees")
public class EmployeeEntity {

    @Id
    @Column(name = "emp_no")
    private Integer empNo;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private Character gender;

    @Column(name = "hire_date")
    private Date hireDate;

    @Column(name = "birth_date")
    private Date birthDate;

    @OneToMany(mappedBy = "employee")
    @ToString.Exclude
    private Collection<TitleEntity> titles;

    @OneToMany(mappedBy = "employee")
    @ToString.Exclude
    private Collection<SalaryEntity> salaries;

    @OneToMany(mappedBy = "empNo")
    @ToString.Exclude
    private Collection<DepartmentEmployee> departments;
}
