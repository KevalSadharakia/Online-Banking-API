package com.bank.api.controllers;

import com.bank.api.dto.EnableNetBankingModel;
import com.bank.api.dto.PersonalDetailsRequest;
import com.bank.api.entity.PersonalDetails;
import com.bank.api.services.PersonalDetailsService;
import com.bank.api.helper.ModelConverter;
import com.bank.api.helper.ValueExtrecterFromPrinciple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class PersonalDetailsControllerTest {

    @InjectMocks
    private PersonalDetailsController personalDetailsController;

    @Mock
    private PersonalDetailsService personalDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateAccount_ValidRequest() {
        PersonalDetailsRequest personalDetailsRequest = new PersonalDetailsRequest();
        personalDetailsRequest.setEmail("test@example.com");

        when(personalDetailsService.isExist(personalDetailsRequest.getEmail())).thenReturn(false);

        PersonalDetails savedPersonalDetails = new PersonalDetails();
        when(personalDetailsService.save(any(PersonalDetails.class))).thenReturn(savedPersonalDetails);

        ResponseEntity<Object> response = personalDetailsController.createAccount(personalDetailsRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedPersonalDetails, response.getBody());
    }

    @Test
    public void testCreateAccount_DuplicateEmail() {
        PersonalDetailsRequest personalDetailsRequest = new PersonalDetailsRequest();
        personalDetailsRequest.setEmail("duplicate@example.com");

        when(personalDetailsService.isExist(personalDetailsRequest.getEmail())).thenReturn(true);

        ResponseEntity<Object> response = personalDetailsController.createAccount(personalDetailsRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email address is used", response.getBody());
    }


    @Test
    public void testEnableNetBanking_ValidRequest() {
        EnableNetBankingModel enableNetBankingModel = new EnableNetBankingModel();
        enableNetBankingModel.setAccountNumber(123);
        enableNetBankingModel.setPhoneNumber("1234567890");

        PersonalDetails personalDetails = new PersonalDetails();
        personalDetails.setContactNumber("1234567890");


        when(personalDetailsService.getDetailsByAccountNumber(enableNetBankingModel.getAccountNumber())).thenReturn(personalDetails);

        when(personalDetailsService.isNetBankingAlreadyEnabled(enableNetBankingModel.getAccountNumber())).thenReturn(false);

        PersonalDetails updatedPersonalDetails = new PersonalDetails();
        when(personalDetailsService.save(any(PersonalDetails.class))).thenReturn(updatedPersonalDetails);

        ResponseEntity<Object> response = personalDetailsController.enableNetBanking(enableNetBankingModel);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedPersonalDetails, response.getBody());
    }

    @Test
    public void testGetAccount() {
        int accountId = 123;
        when(personalDetailsService.getDetailsByAccountNumber(accountId)).thenReturn(new PersonalDetails());

        ResponseEntity<Object> response = personalDetailsController.getAccount(accountId, mock(Principal.class));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(PersonalDetails.class, response.getBody().getClass());
    }
}
