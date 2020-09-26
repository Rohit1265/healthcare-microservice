package com.centene.healthcare.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "dependent")
@Getter
@Setter
//@AllArgsConstructor
@NoArgsConstructor
public class Dependent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(name = "birth_date")
    private Date birthDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="enrollee_id")
    private Enrollee enrolleeId;

    //For Audit track
    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    public Dependent(String name, Date birthDate, Enrollee enrolleeId, Date createdDate, Date updatedDate) {
        this.name = name;
        this.birthDate = birthDate;
        this.enrolleeId = enrolleeId;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
