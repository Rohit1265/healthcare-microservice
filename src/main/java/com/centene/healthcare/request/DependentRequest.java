package com.centene.healthcare.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

/**
 * This class acts as a dependent request
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DependentRequest {

    @NonNull
    @ApiModelProperty(notes = "Name of the Dependent", example = "John Doe", required = true)
    private String name;

    @NonNull
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "UTC")
    @ApiModelProperty(notes = "Date of birth of the Dependent", dataType = "java.util.Date", example = "1993-08-20", required = true)
    private String birthDate;

}
