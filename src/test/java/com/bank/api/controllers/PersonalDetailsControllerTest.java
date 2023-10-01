package com.bank.api.controllers;

import com.bank.api.controllers.PersonalDetailsController;
import com.bank.api.dto.*;
import com.bank.api.entity.PersonalDetails;
import com.bank.api.services.PersonalDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
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
    public void testCreateAccountEmailInUse() {
        PersonalDetailsRequest request = new PersonalDetailsRequest();
        // Initialize request with required data

        when(personalDetailsService.isExist(request.getEmail())).thenReturn(true);

        ResponseEntity<Object> response = personalDetailsController.createAccount(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email address is used.", response.getBody());
    }

    @Test
    public void testGetUsernameNoAccountFound() {
        ForgotUsernameRequest request = new ForgotUsernameRequest();
        // Initialize request with required data

        when(personalDetailsService.getDetailsByAccountNumber(request.getAccountNumber())).thenReturn(null);

        ResponseEntity<Object> response = personalDetailsController.getUsername(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No account found!", response.getBody());
    }
    @Test
    public void testForgotPasswordNoAccountFound() {
        ForgotPasswordRequest request = new ForgotPasswordRequest();

        when(personalDetailsService.getDetailsByAccountNumber(request.getAccountNumber())).thenReturn(null);

        ResponseEntity<Object> response = personalDetailsController.forgotPassword(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No account found!", response.getBody());
    }

}