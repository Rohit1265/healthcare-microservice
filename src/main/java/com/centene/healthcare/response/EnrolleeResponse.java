package com.centene.healthcare.response;

import com.centene.healthcare.request.EnrolleeRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import java.util.List;

/**
 * This class acts as a response of an enrollee
 */
@Getter
@NoArgsConstructor
public class EnrolleeResponse extends EnrolleeRequest {

    private long id;
    private List<DependentResponse> dependents;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date createdDate;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date updatedDate;

    @Builder
    public EnrolleeResponse(String name, String birthDate, boolean activationStatus, long phoneNumber, long id, List<DependentResponse> dependents, Date createdDate, Date updatedDate) {
        super(name, birthDate, activationStatus, phoneNumber);
        this.id = id;
        this.dependents = dependents;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
