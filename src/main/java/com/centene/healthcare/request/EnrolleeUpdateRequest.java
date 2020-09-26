package com.centene.healthcare.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * This class acts as a enrollee request for update
 */
@Getter
@AllArgsConstructor
public class EnrolleeUpdateRequest extends EnrolleeRequest{
    private long id;

    @Builder
    public EnrolleeUpdateRequest(String name, String birthDate, boolean activationStatus, long phoneNumber, long id) {
        super(name, birthDate, activationStatus, phoneNumber);
        this.id = id;
    }
}
