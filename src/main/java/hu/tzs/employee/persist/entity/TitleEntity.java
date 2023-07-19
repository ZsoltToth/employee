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
@Entity(name = "titles")
@Table(name = "titles")
@IdClass(TitleEntity.TitleEntityId.class)
public class TitleEntity {

    @Id
    @Column(name = "emp_no")
    private int empNo;

    @Id
    @Column(name = "title")
    private String title;

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
    public static class TitleEntityId implements Serializable {

        private int empNo;

        private String title;

        private Date fromDate;
    }
}
