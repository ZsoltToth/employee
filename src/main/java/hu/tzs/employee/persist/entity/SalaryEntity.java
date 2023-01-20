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
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity(name = "salaries")
@Table(name="salaries")
@IdClass(SalaryEntity.SalaryEntityId.class)
public class SalaryEntity {


    @Id
    @Column(name = "emp_no")
    private int empNo;

    @Id
    @Column(name = "salary")
    private int salary;

    @Id
    @Column(name = "from_date")
    private Date fromDate;

    @Column(name = "to_date")
    private Date toDate;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "emp_no", updatable = false, insertable = false)
    EmployeeEntity employee;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class SalaryEntityId implements Serializable{

        private int empNo;
        private int salary;
        private Date fromDate;
    }
}
