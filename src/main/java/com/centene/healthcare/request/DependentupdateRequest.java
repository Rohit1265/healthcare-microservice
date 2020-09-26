package com.centene.healthcare.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * This class acts as a dependent request for udpate
 */
@Getter
@AllArgsConstructor
public class DependentupdateRequest extends DependentRequest{
    private long id;

    @Builder
    public DependentupdateRequest(String name, String birthDate, long id) {
        super(name, birthDate);
        this.id = id;
    }

    /* public DependentupdateRequest(String name, String birthDate, long id) {
        super(name, birthDate);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }*/
}
