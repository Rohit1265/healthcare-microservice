package com.centene.healthcare.service;

import com.centene.healthcare.apiresponse.ApiResponse;
import com.centene.healthcare.constants.ApplicationConstants;
import com.centene.healthcare.domain.Dependent;
import com.centene.healthcare.repository.DependentRepository;
import com.centene.healthcare.request.DependentRequest;
import com.centene.healthcare.request.DependentupdateRequest;
import com.centene.healthcare.response.DependentResponse;
import com.centene.healthcare.utility.ApiResponseHelper;
import com.centene.healthcare.utility.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DependentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DependentService.class);

    @Autowired
    private DependentRepository dependentRepository;

    @Autowired
    private ApiResponseHelper apiResponseHelper;

    /**
     * This method takes the request object from Dependent and make a call to repo
     * @param dependentRequest
     * @return
     */
    public DependentResponse add(DependentRequest dependentRequest){
        LOGGER.info("Creates a new dependent!!");
        Dependent dependent = dependentRepository.save(convertRequestToDomain(dependentRequest));
        return convertDomainToResponse(dependent);
    }

    /**
     * This method takes the request object from Enrollee and make a call to repo
     * @param dependentRequest
     * @return
     */
    public DependentResponse modify(DependentupdateRequest dependentRequest){
        try{
            LOGGER.info("Updates an existing dependent!!");
            Optional<Dependent> existingDependent = dependentRepository.findById(dependentRequest.getId());
            if(existingDependent.isPresent()){
                existingDependent.get().setBirthDate(DateUtils.convertStringToDate(dependentRequest.getBirthDate()));
                existingDependent.get().setName(dependentRequest.getName());
                existingDependent.get().setUpdatedDate(DateUtils.getFormattedDate());
                Dependent dependent = dependentRepository.save(existingDependent.get());
                return convertDomainToResponse(dependent);
            }
        }catch (Exception e){
            LOGGER.error("Exception occurred while modifying the dependent records :: "+e.getMessage());
        }
        return null;
    }


    /**
     * This method uses to convert the domain to response
     * @param dependent
     * @return
     */
    public DependentResponse convertDomainToResponse(Dependent dependent) {
        DependentResponse dependentResponse = new DependentResponse(dependent.getName(), DateUtils.convertStringToDate(dependent.getBirthDate()),dependent.getId(), dependent.getCreatedDate(), dependent.getUpdatedDate());
        return dependentResponse;
    }

    /**
     * This method uses to convert the request object into reponse
     * @param dependentRequest
     * @return
     */
    private Dependent convertRequestToDomain(DependentRequest dependentRequest) {
        Date date = DateUtils.getFormattedDate();
        Dependent dependent = new Dependent(dependentRequest.getName(), DateUtils.convertStringToDate(dependentRequest.getBirthDate()),null, date,date);
        return dependent;
    }

    /**
     * This method uses to get all dependents
     * @return
     */
    public List<DependentResponse> all() {
        LOGGER.info("Gets all dependents!!");
        List<DependentResponse> dependentResponses = null;
        List<Dependent> dependents = dependentRepository.findAll();
        if(!CollectionUtils.isEmpty(dependents)){
            dependentResponses = dependents.stream().map(dependent -> {
                return convertDomainToResponse(dependent);
            }).collect(Collectors.toList());
        }
        return dependentResponses;
    }

    /**
     * This method uses to delete the dependent
     * @param id
     * @return
     */
    public ResponseEntity<ApiResponse> delete(long id) {
        LOGGER.info("Deletes an existing dependent!!");
        ResponseEntity<ApiResponse> responseEntity = null;
        Optional<Dependent> dependent = dependentRepository.findById(id);
        if(dependent.isPresent()){
            if(dependent.get().getEnrolleeId() != null){
                responseEntity = apiResponseHelper.getApiResponseWithMessage(ApplicationConstants.DEPENDENT_ADDED_TO_ENROLLEE,ApplicationConstants.FAILURE, false);
            }else{
                dependentRepository.delete(dependent.get());
                responseEntity = apiResponseHelper.getApiResponseWithMessage(ApplicationConstants.DEPENDENT_DELETED,ApplicationConstants.SUCCESS, true);
            }
        }else{
            responseEntity = apiResponseHelper.getApiResponseWithMessage(ApplicationConstants.DEPENDENT_NOT_EXIST,ApplicationConstants.FAILURE, false);
        }
        return responseEntity;
    }
}
