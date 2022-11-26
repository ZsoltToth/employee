package hu.tzs.employee.persist.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "titles")
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


    @Data
    @AllArgsConstructor
    public static class TitleEntityId implements Serializable{

        private int empNo;
        private String title;
        private Date fromDate;
    }
}
