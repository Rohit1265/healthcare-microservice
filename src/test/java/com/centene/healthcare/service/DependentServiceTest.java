package com.centene.healthcare.service;


import com.centene.healthcare.domain.Dependent;
import com.centene.healthcare.repository.DependentRepository;
import com.centene.healthcare.request.DependentRequest;
import com.centene.healthcare.request.DependentupdateRequest;
import com.centene.healthcare.response.DependentResponse;
import com.centene.healthcare.utility.DateUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
public class DependentServiceTest {

    @Mock
    private DependentRepository dependentRepository;

    @InjectMocks
    private DependentService dependentService;


    @Test
    public void addDependentTest(){
        Mockito.when(dependentRepository.save(Mockito.any(Dependent.class))).thenReturn(getDependent());
        DependentResponse enrolleeResponse = dependentService.add(getDependentRequest());
        assertNotNull(enrolleeResponse);
        assertEquals("To check whether name is equal or not","Steve",enrolleeResponse.getName());
    }

    @Test
    public void modifyEnrolleeTest(){
        Mockito.when(dependentRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(getDependent()));
        Mockito.when(dependentRepository.save(Mockito.any(Dependent.class))).thenReturn(getDependent());
        DependentResponse dependentResponse = dependentService.modify(getUpdateDependentRequest());
        assertNotNull(dependentResponse);
        assertEquals("To check whether name is equal or not","Steve",dependentResponse.getName());
    }

    @Test
    public void getAllEnrolleesTest(){
        Mockito.when(dependentRepository.findAll()).thenReturn(getDependents());
        List<DependentResponse> enrolleeResponses = dependentService.all();
        assertNotNull(enrolleeResponses);
        assertEquals("when all enrollees size is 1",1,enrolleeResponses.size());
    }


    @Test
    public void getAllEnrolleesTestWhenItIsNull(){
        Mockito.when(dependentRepository.findAll()).thenReturn(null);
        List<DependentResponse> enrolleeResponses = dependentService.all();
        assertNull(enrolleeResponses);
    }

    private DependentRequest getDependentRequest(){
        DependentRequest dependentRequest = new DependentRequest("John Doe","15-08-1995");
        return dependentRequest;
    }

    private DependentupdateRequest getUpdateDependentRequest(){
        DependentupdateRequest dependentupdateRequest = new DependentupdateRequest("John Doe","15-08-1995",1);
        return dependentupdateRequest;
    }

    private List<Dependent> getDependents(){
        List<Dependent> dependents = new ArrayList<>();
        dependents.add(getDependent());
        return dependents;
    }

    private Dependent getDependent(){
        Dependent dependent = new Dependent("Steve", DateUtils.convertStringToDate("15-08-1995"),null,new Date(), new Date());
        return dependent;
    }
}
