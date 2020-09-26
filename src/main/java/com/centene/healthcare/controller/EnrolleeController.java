package com.centene.healthcare.controller;

import com.centene.healthcare.apiresponse.ApiResponse;
import com.centene.healthcare.request.EnrolleeRequest;
import com.centene.healthcare.request.EnrolleeUpdateRequest;
import com.centene.healthcare.service.EnrolleeService;
import com.centene.healthcare.utility.ApiResponseHelper;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * This controller handles the request and response of the enrollee details
 */
@RequestMapping("/enrollee")
@RestController
@ApiOperation("This performs all operations against Enrollee")
public class EnrolleeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EnrolleeController.class);

    @Autowired
    private ApiResponseHelper apiResponseHelper;

    @Autowired
    private EnrolleeService enrolleeService;

    /**
     * This method handles the request from front end to add the Enrollee
     * @param enrolleeRequest
     * @return
     */
    @PostMapping("/add")
    @ApiOperation(value = "Create Enrollee", notes = "This method creates a new Enrollee")
    public ResponseEntity<ApiResponse> add(@Validated @RequestBody EnrolleeRequest enrolleeRequest){
        return apiResponseHelper.getApiResponse(enrolleeService.add(enrolleeRequest), true);
    }

    /**
     * This method handles the request from front end to update the Enrollee
     * @param enrolleeRequest
     * @return
     */
    @PutMapping("/modify")
    @ApiOperation(value = "Update Enrollee", notes = "This method updates a new Enrollee")
    public ResponseEntity<ApiResponse> modify(@Validated @RequestBody EnrolleeUpdateRequest enrolleeRequest){
        return apiResponseHelper.getApiResponse(enrolleeService.modify(enrolleeRequest), false);
    }

    /**
     * This method uses to fetch the all enrollees
     * @return
     */
    @GetMapping("/all")
    @ApiOperation(value = "Fetch Enrollees", notes = "This method gets all Enrollees")
    public ResponseEntity<ApiResponse> all(){
        return apiResponseHelper.getApiResponseForAll(enrolleeService.all());
    }

    /**
     * This method handles the request from front end to delete the Enrollee
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{enrollId}")
    @ApiOperation(value = "Delete Enrollee", notes = "This method deletes Enrollee by id")
    public ResponseEntity<ApiResponse> deleteEnrollee(@PathVariable("enrollId") long id){
        return enrolleeService.delete(id);
    }

    @PostMapping("/addDependentToEnrollee")
    @ApiOperation(value = "add dependent to Enrollee", notes = "This method adds dependent to Enrollee")
    public ResponseEntity<ApiResponse> addDependentToEnrollee(@RequestParam(value = "dependentId") long dependentId, @RequestParam(value = "enrolleeId") long enrolleeId){
        return enrolleeService.addDependentToEnrollee(dependentId, enrolleeId);
    }

    /**
     * This method handles the request from front end to delete the dependent from Enrollee
     * @param dependentId
     * @return
     */
    @DeleteMapping("/delete/dependent/{dependentId}")
    @ApiOperation(value = "delete dependent from Enrollee", notes = "This method deletes dependent from Enrollee")
    public ResponseEntity<ApiResponse> deleteDependentFromEnrollee(@PathVariable("dependentId") long dependentId){
        return enrolleeService.deleteDependentFromEnrollee(dependentId);
    }
}
