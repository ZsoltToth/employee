package hu.tzs.employee.persist.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "departments")
@Table(name = "departments")
public class DepartmentEntity {

    @Id
    @Column(name = "dept_no")
    private String departmentNo;

    @Column(name = "dept_name")
    private String name;


}
