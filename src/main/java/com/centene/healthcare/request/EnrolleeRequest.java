package com.centene.healthcare.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.Date;

/**
 * This class acts as a request object
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EnrolleeRequest {
    @NonNull
    @ApiModelProperty(notes = "Name of the Enrollee", example = "John Doe", required = true)
    private String name;

    @NonNull
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "UTC")
    @ApiModelProperty(notes = "Date of birth of the Enrollee", dataType = "java.util.Date", example = "1993-08-20", required = true)
    private String birthDate;

    @ApiModelProperty(notes = "Status of the Enrollee", example = "true", required = true)
    private boolean activationStatus;

    @JsonIgnore
    private long phoneNumber;

}
