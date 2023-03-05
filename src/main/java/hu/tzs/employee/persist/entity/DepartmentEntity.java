package hu.tzs.employee.persist.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity(name = "departments")
@Table(name = "departments")
public class DepartmentEntity {

    @Id
    @Column(name = "dept_no")
    private String departmentNo;

    @Column(name = "dept_name")
    private String name;


}
