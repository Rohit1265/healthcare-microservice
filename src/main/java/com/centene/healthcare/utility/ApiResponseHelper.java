package com.centene.healthcare.utility;

import com.centene.healthcare.apiresponse.ApiResponse;
import com.centene.healthcare.constants.ApplicationConstants;
import com.centene.healthcare.response.DependentResponse;
import com.centene.healthcare.response.EnrolleeResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * THis class helps to generate universal API response objects
 */
@Component
public class ApiResponseHelper {

    /**
     * This method uses to convert the object to universal API reponse
     * @param enrolleeResponse
     * @param isCreated
     * @return
     */
    public ResponseEntity<ApiResponse> getApiResponse(EnrolleeResponse enrolleeResponse, boolean isCreated){
        ResponseEntity<ApiResponse> responseEntity;
        if(enrolleeResponse != null){
            ApiResponse<EnrolleeResponse> apiResponse = new ApiResponse<>();
            apiResponse.setType(enrolleeResponse);apiResponse.setMessage(ApplicationConstants.SUCCESS);apiResponse.setStatus(true);
            responseEntity = new ResponseEntity<>(apiResponse, isCreated ? HttpStatus.CREATED : HttpStatus.OK);
        }else{
            return getApiResponseWithMessage(ApplicationConstants.EXCEPTION_OCCURRED,ApplicationConstants.FAILURE, false);
        }
        return responseEntity;
    }

    /**
     * This method uses to convert the object to universal API reponse
     * @param enrolleeResponses
     * @return
     */
    public ResponseEntity<ApiResponse> getApiResponseForAll(List<EnrolleeResponse> enrolleeResponses){
        ResponseEntity<ApiResponse> responseEntity;
        if(enrolleeResponses != null){
            ApiResponse<List<EnrolleeResponse>> apiResponse = new ApiResponse<>();
            apiResponse.setType(enrolleeResponses);apiResponse.setMessage(ApplicationConstants.SUCCESS);apiResponse.setStatus(true);
            responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }else{
            return getApiResponseWithMessage(ApplicationConstants.EXCEPTION_OCCURRED,ApplicationConstants.FAILURE, false);
        }
        return responseEntity;
    }

    /**
     * This method uses to convert the object to universal API reponse
     * @param dependentResponses
     * @return
     */
    public ResponseEntity<ApiResponse> getApiResponseForAllForDependents(List<DependentResponse> dependentResponses){
        ResponseEntity<ApiResponse> responseEntity;
        if(dependentResponses != null){
            ApiResponse<List<DependentResponse>> apiResponse = new ApiResponse<>();
            apiResponse.setType(dependentResponses);apiResponse.setMessage(ApplicationConstants.SUCCESS);apiResponse.setStatus(true);
            responseEntity = new ResponseEntity<>(apiResponse, HttpStatus.OK);
        }else{
            return getApiResponseWithMessage(ApplicationConstants.EXCEPTION_OCCURRED,ApplicationConstants.FAILURE, false);
        }
        return responseEntity;
    }

    /**
     * This method uses to convert the object to universal API reponse
     * @param dependentResponse
     * @param isCreated
     * @return
     */
    public ResponseEntity<ApiResponse> getApiResponseForDependent(DependentResponse dependentResponse, boolean isCreated){
        ResponseEntity<ApiResponse> responseEntity;
        if(dependentResponse != null){
            ApiResponse<DependentResponse> apiResponse = new ApiResponse<>();
            apiResponse.setType(dependentResponse);apiResponse.setMessage(ApplicationConstants.SUCCESS);apiResponse.setStatus(true);
            responseEntity = new ResponseEntity<>(apiResponse, isCreated ? HttpStatus.CREATED : HttpStatus.OK);
        }else{
            return getApiResponseWithMessage(ApplicationConstants.EXCEPTION_OCCURRED,ApplicationConstants.FAILURE, false);
        }
        return responseEntity;
    }

    /**
     * This method uses to generate the API Reponse for string case.
     * @param type
     * @param message
     * @param status
     * @return
     */
    public ResponseEntity<ApiResponse> getApiResponseWithMessage(String type, String message, boolean status){
        ApiResponse<String> apiResponse = new ApiResponse<>();
        apiResponse.setType(type);apiResponse.setMessage(message);apiResponse.setStatus(status);
        ResponseEntity<ApiResponse> responseEntity = new ResponseEntity<>(apiResponse,status ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
        return responseEntity;
    }
}
