package com.bank.api.controllers;

import com.bank.api.dto.JWTRequest;
import com.bank.api.dto.JWTResponse;
import com.bank.api.services.PersonalDetailsService;
import com.bank.api.auth.JwtHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private JwtHelper jwtHelper;

    @Mock
    private PersonalDetailsService personalDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testLogin_ValidCredentials() {
        JWTRequest jwtRequest = new JWTRequest("username", "password");

        when(personalDetailsService.isLoginCredentialValid(jwtRequest)).thenReturn(true);

        when(jwtHelper.generateToken(jwtRequest.getUsername())).thenReturn("mockedToken");

        ResponseEntity<Object> response = authenticationController.login(jwtRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(JWTResponse.class, response.getBody().getClass());

        JWTResponse jwtResponse = (JWTResponse) response.getBody();
        assertEquals("username", jwtResponse.getUsername());
        assertEquals("mockedToken", jwtResponse.getToken());
    }

    @Test
    public void testLogin_InvalidCredentials() {
        JWTRequest jwtRequest = new JWTRequest("invalidUser", "invalidPassword");

        when(personalDetailsService.isLoginCredentialValid(jwtRequest)).thenReturn(false);

        ResponseEntity<Object> response = authenticationController.login(jwtRequest);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid User", response.getBody());
    }

}