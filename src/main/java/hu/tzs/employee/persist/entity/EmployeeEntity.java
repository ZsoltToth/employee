package hu.tzs.employee.persist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "employees")
public class EmployeeEntity {

    @Id
    @Column(name="emp_no")
    private int empNo;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;
    private char gender;

    @Column(name="hire_date")
    private Date hireDate;

    @Column(name="birth_date")
    private Date birthDate;

    @OneToMany(mappedBy = "emp_no")
    private Collection<TitleEntity> titles;
//    private String title;
//    private int salary;
//    private Department department;
}