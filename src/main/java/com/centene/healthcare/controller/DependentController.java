package com.centene.healthcare.controller;

import com.centene.healthcare.apiresponse.ApiResponse;
import com.centene.healthcare.request.DependentRequest;
import com.centene.healthcare.request.DependentupdateRequest;
import com.centene.healthcare.service.DependentService;
import com.centene.healthcare.utility.ApiResponseHelper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dependent")
@ApiOperation("This performs all operations against Dependent")
public class DependentController {

    @Autowired
    private DependentService dependentService;

    @Autowired
    private ApiResponseHelper apiResponseHelper;

    /**
     * This method handles the request from front end to add the Enrollee
     * @param dependentRequest
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "Create Dependent", notes = "This method creates a new Dependent")
    public ResponseEntity<ApiResponse> add(@Validated @RequestBody DependentRequest dependentRequest){
        return apiResponseHelper.getApiResponseForDependent(dependentService.add(dependentRequest), true);
    }

    /**
     * This method handles the request from front end to update the Enrollee
     * @param dependentupdateRequest
     * @return
     */
    @PutMapping("/modify")
    @ApiOperation(value = "Update Dependent", notes = "This method udpates the existing Dependent")
    public ResponseEntity<ApiResponse> modify(@Validated @RequestBody DependentupdateRequest dependentupdateRequest){
        return apiResponseHelper.getApiResponseForDependent(dependentService.modify(dependentupdateRequest), false);
    }

    /**
     * This method handles the request from front end to delete the Enrollee
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{dependentId}")
    @ApiOperation(value = "Delete Dependent", notes = "This method deletes Dependent by id")
    public ResponseEntity<ApiResponse> deleteDependent(@PathVariable("dependentId") long id){
        return dependentService.delete(id);
    }

    /**
     * This method uses to fetch the all dependents
     * @return
     */
    @GetMapping("/all")
    @ApiOperation(value = "Fetch Dependents", notes = "This method gets all the Dependents")
    public ResponseEntity<ApiResponse> all(){
        return apiResponseHelper.getApiResponseForAllForDependents(dependentService.all());
    }

}
