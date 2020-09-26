package com.centene.healthcare.response;

import com.centene.healthcare.domain.Enrollee;
import com.centene.healthcare.request.DependentRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * This class acts as a response of an enrollee
 */
@Getter
@NoArgsConstructor
public class DependentResponse extends DependentRequest {

    private long id;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date createdDate;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private Date updatedDate;

    @Builder
    public DependentResponse(String name, String birthDate, long id, Date createdDate, Date updatedDate) {
        super(name, birthDate);
        this.id = id;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }
}
