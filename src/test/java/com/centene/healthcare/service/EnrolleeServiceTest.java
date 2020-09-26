package com.centene.healthcare.service;

import com.centene.healthcare.apiresponse.ApiResponse;
import com.centene.healthcare.constants.ApplicationConstants;
import com.centene.healthcare.domain.Dependent;
import com.centene.healthcare.domain.Enrollee;
import com.centene.healthcare.repository.DependentRepository;
import com.centene.healthcare.repository.EnrolleeRepository;
import com.centene.healthcare.request.EnrolleeRequest;
import com.centene.healthcare.request.EnrolleeUpdateRequest;
import com.centene.healthcare.response.DependentResponse;
import com.centene.healthcare.response.EnrolleeResponse;
import com.centene.healthcare.utility.ApiResponseHelper;
import com.centene.healthcare.utility.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
public class EnrolleeServiceTest {

    @InjectMocks
    private EnrolleeService enrolleeService;

     @Mock
    private EnrolleeRepository enrolleeRepository;

     @Mock
    private DependentRepository dependentRepository;

     @Mock
    private ApiResponseHelper apiResponseHelper;

     @Mock
    private DependentService dependentService;

    @Test
    public void addEnrolleeTest(){
        Mockito.when(enrolleeRepository.save(Mockito.any(Enrollee.class))).thenReturn(getEnrolle());
        EnrolleeResponse enrolleeResponse = enrolleeService.add(getEnrolleRequest());
        assertNotNull(enrolleeResponse);
        assertEquals("To check whether name is equal or not","John Doe",enrolleeResponse.getName());
    }

    @Test
    public void modifyEnrolleeTest(){
        Mockito.when(enrolleeRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(getEnrolle()));
        Mockito.when(enrolleeRepository.save(Mockito.any(Enrollee.class))).thenReturn(getEnrolle());
        EnrolleeResponse enrolleeResponse = enrolleeService.modify(getUpdateEnrolleRequest());
        assertNotNull(enrolleeResponse);
        assertEquals("To check whether name is equal or not","John Doe",enrolleeResponse.getName());
    }

    @Test
    public void deleteEnrolleeTestWhenSuccessCase(){
        Enrollee enrollee = getEnrolle(); enrollee.setDependents(getDependents());
        Optional<Enrollee> enrolle = Optional.of(enrollee);
        Mockito.when(enrolleeRepository.findById(Mockito.anyLong())).thenReturn(enrolle);
        Mockito.when(dependentRepository.saveAll(Mockito.any())).thenReturn(getDependents());
        Mockito.when(apiResponseHelper.getApiResponseWithMessage(Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean())).thenReturn(getApiResponseWithMessage(ApplicationConstants.ENROLLEE_DELETED,ApplicationConstants.SUCCESS, true));
        Mockito.doNothing().when(enrolleeRepository).delete(Mockito.any(Enrollee.class));
        ResponseEntity<ApiResponse> responseEntity = enrolleeService.delete(1);
        assertNotNull(responseEntity);
        assertEquals("delete enrollee when it is success",ApplicationConstants.SUCCESS,responseEntity.getBody().getMessage());
        assertEquals("delete enrollee when status is 200",HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
    }


    @Test
    public void deleteEnrolleeTestWhenEnrolleeIsNull(){
        Optional<Enrollee> enrolle = Optional.ofNullable(null);
        Mockito.when(enrolleeRepository.findById(Mockito.anyLong())).thenReturn(enrolle);
        Mockito.when(apiResponseHelper.getApiResponseWithMessage(Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean())).thenReturn(getApiResponseWithMessage(ApplicationConstants.ENROLLEE_NOT_EXIST,ApplicationConstants.FAILURE, false));
        ResponseEntity<ApiResponse> responseEntity = enrolleeService.delete(1);
        assertNotNull(responseEntity);
        assertEquals("delete enrollee when enrollee is null",ApplicationConstants.FAILURE,responseEntity.getBody().getMessage());
        assertEquals("delete enrollee when status is 400",HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
    }


    @Test
    public void deleteDependentFromEnrolleeTestWhenSuccessCase(){
        Dependent dependent = getDependent();
        Mockito.when(dependentRepository.findById(Mockito.anyLong())).thenReturn( Optional.of(dependent));
        Mockito.when(dependentRepository.save(Mockito.any())).thenReturn(getDependents());
        Mockito.when(apiResponseHelper.getApiResponseWithMessage(Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean())).thenReturn(getApiResponseWithMessage(ApplicationConstants.DEPENDENT_REMOVED_FROM_ENROLLEE,ApplicationConstants.SUCCESS, true));
        ResponseEntity<ApiResponse> responseEntity = enrolleeService.deleteDependentFromEnrollee(1);
        assertNotNull(responseEntity);
        assertEquals("delete dependent from enrollee when it is success",ApplicationConstants.SUCCESS,responseEntity.getBody().getMessage());
        assertEquals("delete dependent from enrollee when status is 200",HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
    }

    @Test
    public void deleteDependentFromEnrolleeTestWhenDependentIsNull(){
        Optional<Dependent> dependent = Optional.ofNullable(null);
        Mockito.when(dependentRepository.findById(Mockito.anyLong())).thenReturn(dependent);
        Mockito.when(apiResponseHelper.getApiResponseWithMessage(Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean())).thenReturn(getApiResponseWithMessage(ApplicationConstants.DEPENDENT_NOT_EXIST,ApplicationConstants.FAILURE, false));
        ResponseEntity<ApiResponse> responseEntity = enrolleeService.deleteDependentFromEnrollee(1);
        assertNotNull(responseEntity);
        assertEquals("delete dependent from enrollee when dependent is null",ApplicationConstants.FAILURE,responseEntity.getBody().getMessage());
        assertEquals("delete dependent from enrollee when status is 400",HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
    }


    @Test
    public void addDependentToEnrolleeTestWhenSuccessCase(){
        Dependent dependent = getDependent();
        dependent.setEnrolleeId(null);
        Mockito.when(dependentRepository.findById(Mockito.anyLong())).thenReturn( Optional.of(dependent));

        Enrollee enrollee = getEnrolle(); enrollee.setDependents(getDependents());
        Optional<Enrollee> enrolle = Optional.of(enrollee);
        Mockito.when(enrolleeRepository.findById(Mockito.anyLong())).thenReturn(enrolle);

        Mockito.when(dependentService.convertDomainToResponse(Mockito.any(Dependent.class))).thenReturn(getDependentResponse());

        Mockito.when(dependentRepository.save(Mockito.any())).thenReturn(getDependent());

        Mockito.when(apiResponseHelper.getApiResponse(Mockito.any(EnrolleeResponse.class), Mockito.anyBoolean())).thenReturn(getApiResponse(getEnrolleeReponse(), false));
        ResponseEntity<ApiResponse> responseEntity = enrolleeService.addDependentToEnrollee(1,1);
        assertNotNull(responseEntity);
        assertEquals("delete dependent from enrollee when it is success",ApplicationConstants.SUCCESS,responseEntity.getBody().getMessage());
        assertEquals("delete dependent from enrollee when status is 200",HttpStatus.OK.value(),responseEntity.getStatusCodeValue());
    }


    @Test
    public void addDependentToEnrolleeTestWhenDependentIsAddedToEnrollee(){
        Dependent dependent = getDependent();
        Mockito.when(dependentRepository.findById(Mockito.anyLong())).thenReturn( Optional.of(dependent));
        Mockito.when(apiResponseHelper.getApiResponseWithMessage(Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean())).thenReturn(getApiResponseWithMessage(ApplicationConstants.DEPENDENT_ALREADY_ADDED_TO_ENROLLEE,ApplicationConstants.FAILURE, false));
        ResponseEntity<ApiResponse> responseEntity = enrolleeService.addDependentToEnrollee(1,1);
        assertNotNull(responseEntity);
        assertEquals("delete dependent from enrollee when it is failed",ApplicationConstants.FAILURE,responseEntity.getBody().getMessage());
        assertEquals("delete dependent from enrollee when status is 400",HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
    }

    @Test
    public void addDependentToEnrolleeTestWhenDependentIsNull(){
        Optional<Dependent> dependent = Optional.ofNullable(null);
        Mockito.when(dependentRepository.findById(Mockito.anyLong())).thenReturn(dependent);
        Mockito.when(apiResponseHelper.getApiResponseWithMessage(Mockito.anyString(), Mockito.anyString(), Mockito.anyBoolean())).thenReturn(getApiResponseWithMessage(ApplicationConstants.DEPENDENT_NOT_EXIST,ApplicationConstants.FAILURE, false));
        ResponseEntity<ApiResponse> responseEntity = enrolleeService.addDependentToEnrollee(1,1);
        assertNotNull(responseEntity);
        assertEquals("delete dependent from enrollee when it is failed",ApplicationConstants.FAILURE,responseEntity.getBody().getMessage());
        assertEquals("delete dependent from enrollee when status is 400",HttpStatus.BAD_REQUEST.value(),responseEntity.getStatusCodeValue());
    }

    @Test
    public void getAllEnrolleesTest(){
        Mockito.when(enrolleeRepository.findAll()).thenReturn(getEnrollees());
        List<EnrolleeResponse> enrolleeResponses = enrolleeService.all();
        assertNotNull(enrolleeResponses);
        assertEquals("when all enrollees size is 1",1,enrolleeResponses.size());
    }


    @Test
    public void getAllEnrolleesTestWhenItIsNull(){
        Mockito.when(enrolleeRepository.findAll()).thenReturn(null);
        List<EnrolleeResponse> enrolleeResponses = enrolleeService.all();
        assertNull(enrolleeResponses);
    }

    /**
     * This method uses to get the enrollee request object
     * @return
     */
    private EnrolleeRequest getEnrolleRequest() {
        EnrolleeRequest enrolleeRequest = new EnrolleeRequest("John Doe", "15-08-1995",true,2134l);
        return enrolleeRequest;
    }
    /**
     * This method uses to get the enrollee request object
     * @return
     */
    private EnrolleeUpdateRequest getUpdateEnrolleRequest() {
        EnrolleeUpdateRequest enrolleeRequest = new EnrolleeUpdateRequest("John Doe", "15-08-1995",true,2134l,1);
        return enrolleeRequest;
    }

    private List<Dependent> getDependents(){
        List<Dependent> dependents = new ArrayList<>();
        dependents.add(getDependent());
        return dependents;
    }

    private Dependent getDependent(){
        Dependent dependent = new Dependent("Steve",DateUtils.convertStringToDate("15-08-1995"),getEnrolle(),new Date(), new Date());
        return dependent;
    }

    private List<Enrollee> getEnrollees(){
        List<Enrollee> enrollees = new ArrayList<>();
        enrollees.add(getEnrolle());
        return enrollees;
    }


    /**
     * This method uses to get the enrollee domain object
     * @return
     */
    private Enrollee getEnrolle() {
        Enrollee enrollee = new Enrollee("John Doe", DateUtils.convertStringToDate("15-08-1995"),12313l,true,new Date(),
                new Date(),null);
        return enrollee;
    }

    private DependentResponse getDependentResponse(){
        DependentResponse dependentResponse = new DependentResponse("John Doe", DateUtils.convertStringToDate(new Date()),1, new Date(), new Date());
        return dependentResponse;
    }

    private EnrolleeResponse getEnrolleeReponse(){
        EnrolleeResponse enrolleeResponse = new EnrolleeResponse("John Doe", "15-08-1995", true, 1,1,null, new Date(), new Date());
        return enrolleeResponse;
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
}
