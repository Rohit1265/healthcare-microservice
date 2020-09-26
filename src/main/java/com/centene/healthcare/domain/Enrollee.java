package com.centene.healthcare.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * This class is refer to Database table.
 */
@Entity
@Table(name = "enrollee")
@Getter
@Setter
@NoArgsConstructor
public class Enrollee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @Column(name = "birth_date")
    private Date birthDate;

    @Column(name = "phone_number")
    private long phoneNumber;

    @Column(name = "activation_status")
    boolean activationStatus;

    //For Audit track
    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "updated_date")
    private Date updatedDate;

    @OneToMany(mappedBy="enrolleeId")
    private List<Dependent> dependents;

    public Enrollee(String name, Date birthDate, long phoneNumber, boolean activationStatus, Date createdDate, Date updatedDate, List<Dependent> dependents) {
        this.name = name;
        this.birthDate = birthDate;
        this.phoneNumber = phoneNumber;
        this.activationStatus = activationStatus;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.dependents = dependents;
    }
}
