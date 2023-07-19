package hu.tzs.employee.persist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "dept_emp")
@Table(name = "dept_emp")
@IdClass(DepartmentEmployee.DepartmentEmployeeId.class)
public class DepartmentEmployee {

    @Id
    @Column(name = "emp_no")
    private int empNo;

    @Id
    @Column(name = "dept_no")
    private String deptNo;

    @Column(name = "from_date")
    private Date fromDate;

    @Column(name = "to_date")
    private Date toDate;

    @ManyToOne
    @JoinColumn(name = "emp_no", insertable = false, updatable = false)
    @ToString.Exclude
    private EmployeeEntity employee;

    @ManyToOne
    @JoinColumn(name = "dept_no", insertable = false, updatable = false)
    @ToString.Exclude
    private DepartmentEntity department;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class DepartmentEmployeeId implements Serializable {

        private String deptNo;

        private int empNo;
    }
}
