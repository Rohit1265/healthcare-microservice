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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnrolleeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnrolleeService.class);

    @Autowired
    private EnrolleeRepository enrolleeRepository;

    @Autowired
    private DependentRepository dependentRepository;

    @Autowired
    private DependentService dependentService;

    @Autowired
    private ApiResponseHelper apiResponseHelper;

    /**
     * This method takes the request object from Enrollee and make a call to repo
     * @param enrolleeRequest
     * @return
     */
    public EnrolleeResponse add(EnrolleeRequest enrolleeRequest){
        LOGGER.info("Creates a new enrollee!!");
        Enrollee enrollee = enrolleeRepository.save(convertRequestToDomain(enrolleeRequest));
        return convertDomainToResponse(enrollee);
    }

    /**
     * This method takes the request object from Enrollee and make a call to repo
     * @param enrolleeRequest
     * @return
     */
    public EnrolleeResponse modify(EnrolleeUpdateRequest enrolleeRequest){
        try{
            LOGGER.info("Modifies an existing enrollee!!");
            Optional<Enrollee> existingEnrollee = enrolleeRepository.findById(enrolleeRequest.getId());
            if(existingEnrollee.isPresent()){
                existingEnrollee.get().setBirthDate(DateUtils.convertStringToDate(enrolleeRequest.getBirthDate()));
                existingEnrollee.get().setName(enrolleeRequest.getName());
                existingEnrollee.get().setUpdatedDate(DateUtils.getFormattedDate());
                Enrollee enrollee = enrolleeRepository.save(existingEnrollee.get());
                return convertDomainToResponse(enrollee);
            }
        }catch (Exception e){
            LOGGER.error("Exception occurred while modifying the dependent records :: "+e.getMessage());
        }
        return null;
    }

    /**
     * This method uses to delete the enrollee
     */
    public ResponseEntity<ApiResponse> delete(long id){
        LOGGER.info("deletes existing enrollee!!");
        ResponseEntity<ApiResponse> responseEntity = null;
        try {
            Optional<Enrollee> enrollee = enrolleeRepository.findById(id);
            if(enrollee.isPresent()){
                List<Dependent> dependents = enrollee.get().getDependents();
                if(!CollectionUtils.isEmpty(dependents)){
                    //TO remove dependents from an Enrollee.
                    dependents.forEach(dependent -> dependent.setEnrolleeId(null));
                    dependentRepository.saveAll(dependents);
                }
                enrolleeRepository.delete(enrollee.get());
                responseEntity = apiResponseHelper.getApiResponseWithMessage(ApplicationConstants.ENROLLEE_DELETED,ApplicationConstants.SUCCESS, true);
            }else{
                responseEntity = apiResponseHelper.getApiResponseWithMessage(ApplicationConstants.ENROLLEE_NOT_EXIST,ApplicationConstants.FAILURE, false);
            }
            return responseEntity;
        }catch (Exception e){
            LOGGER.error("Exception occurred while deleting the enrollee :: "+e.getMessage());
            responseEntity = apiResponseHelper.getApiResponseWithMessage(ApplicationConstants.EXCEPTION_OCCURRED,ApplicationConstants.FAILURE, false);
        }
        return responseEntity;
    }

    /**
     * This method uses to delete the dependent from Enrollee
     */
    public ResponseEntity<ApiResponse> deleteDependentFromEnrollee(long dependentId){
        LOGGER.info("delete the dependent from Enrollee!!");
        ResponseEntity<ApiResponse> responseEntity = null;
        try {
            Optional<Dependent> dependent = dependentRepository.findById(dependentId);
            if(dependent.isPresent()){
                if(dependent.get().getEnrolleeId() != null){
                    dependent.get().setEnrolleeId(null);
                    dependentRepository.save(dependent.get());
                    responseEntity = apiResponseHelper.getApiResponseWithMessage(ApplicationConstants.DEPENDENT_REMOVED_FROM_ENROLLEE,ApplicationConstants.SUCCESS, true);
                }
            }else{
                responseEntity = apiResponseHelper.getApiResponseWithMessage(ApplicationConstants.DEPENDENT_NOT_EXIST,ApplicationConstants.FAILURE, false);
            }
        }catch (Exception e){
            LOGGER.error("Exception occurred while deleting the dependent from Enrollee :: "+e.getMessage());
            responseEntity = apiResponseHelper.getApiResponseWithMessage(ApplicationConstants.EXCEPTION_OCCURRED,ApplicationConstants.FAILURE, false);
        }
        return responseEntity;
    }

    /**
     * This method uses to convert the domain to response
     * @param enrollee
     * @return
     */
    private EnrolleeResponse convertDomainToResponse(Enrollee enrollee) {
        EnrolleeResponse enrolleeResponse = new EnrolleeResponse(enrollee.getName(), DateUtils.convertStringToDate(enrollee.getBirthDate()), enrollee.isActivationStatus(),
                enrollee.getPhoneNumber(),enrollee.getId(), convertDependentsDomainToResponse(enrollee.getDependents()), enrollee.getCreatedDate(), enrollee.getUpdatedDate());
        return enrolleeResponse;
    }

    /**
     * This method uses to convert the dependents list to reponse
     * @param dependents
     * @return
     */
    private List<DependentResponse> convertDependentsDomainToResponse(List<Dependent> dependents) {
        List<DependentResponse> dependentResponses = null;
        if(!CollectionUtils.isEmpty(dependents)){
            dependentResponses = dependents.stream().map(dependent -> {
                return dependentService.convertDomainToResponse(dependent);
            }).collect(Collectors.toList());
        }
        return dependentResponses;
    }

    /**
     * This method uses to convert the request object into reponse
     * @param enrolleeRequest
     * @return
     */
    private Enrollee convertRequestToDomain(EnrolleeRequest enrolleeRequest) {
        Date date = DateUtils.getFormattedDate();
        Enrollee enrollee = new Enrollee(enrolleeRequest.getName(), DateUtils.convertStringToDate(enrolleeRequest.getBirthDate()),enrolleeRequest.getPhoneNumber(),enrolleeRequest.isActivationStatus(),date,
                date,null);
        return enrollee;
    }

    /**
     * This method uses to add the dependent to enrollee
     * @param dependentId
     * @param enrolleeId
     * @return
     */
    public ResponseEntity<ApiResponse> addDependentToEnrollee(long dependentId, long enrolleeId) {
        try{
            LOGGER.info("add the dependent to enrollee!!");
            Optional<Dependent> dependent = dependentRepository.findById(dependentId);
            if(dependent.isPresent()){
                if(dependent.get().getEnrolleeId() == null){
                    Optional<Enrollee> enrollee = enrolleeRepository.findById(enrolleeId);
                    if(enrollee.isPresent()){
                        dependent.get().setEnrolleeId(enrollee.get());
                        dependentRepository.save(dependent.get());
                        EnrolleeResponse enrolleeResponse = convertDomainToResponse(enrollee.get());
                        if(CollectionUtils.isEmpty(enrolleeResponse.getDependents())){
                            enrolleeResponse.getDependents().add(dependentService.convertDomainToResponse(dependent.get()));
                        }
                        return apiResponseHelper.getApiResponse(enrolleeResponse, false);
                    }else{
                        return apiResponseHelper.getApiResponseWithMessage(ApplicationConstants.ENROLLEE_NOT_EXIST,ApplicationConstants.FAILURE, false);
                    }
                }else{
                    return apiResponseHelper.getApiResponseWithMessage(ApplicationConstants.DEPENDENT_ALREADY_ADDED_TO_ENROLLEE + dependent.get().getEnrolleeId().getName(),ApplicationConstants.FAILURE, false);
                }
            }else{
                return apiResponseHelper.getApiResponseWithMessage(ApplicationConstants.DEPENDENT_NOT_EXIST,ApplicationConstants.FAILURE, false);
            }
        }catch (Exception e){
            LOGGER.error("Exception occurred while adding the dependent to enrollee :: "+e.getMessage());
            return apiResponseHelper.getApiResponseWithMessage(ApplicationConstants.EXCEPTION_OCCURRED,ApplicationConstants.FAILURE, false);
        }
    }

    /**
     * This method uses to get the all enrollee
     * @return
     */
    public List<EnrolleeResponse> all() {
        LOGGER.info("This gets all enrollees");
        List<Enrollee> enrollees = enrolleeRepository.findAll();
        List<EnrolleeResponse> enrolleeResponses = null;
        if(!CollectionUtils.isEmpty(enrollees)){
            enrolleeResponses = enrollees.stream().map(enrollee -> {
                return convertDomainToResponse(enrollee);
            }).collect(Collectors.toList());
        }
        return enrolleeResponses;
    }
}
