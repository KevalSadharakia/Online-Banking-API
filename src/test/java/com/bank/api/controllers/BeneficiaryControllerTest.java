package com.bank.api.controllers;

import com.bank.api.dto.BeneficiaryRequest;
import com.bank.api.logics.BeneficiaryLogics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class BeneficiaryControllerTest {

    @InjectMocks
    private BeneficiaryController beneficiaryController;

    @Mock
    private BeneficiaryLogics beneficiaryLogics;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddBeneficiary() {
        BeneficiaryRequest beneficiaryRequest = new BeneficiaryRequest();
        Principal principal = mock(Principal.class);

        when(beneficiaryLogics.addBeneficiary(beneficiaryRequest, principal))
                .thenReturn(ResponseEntity.ok("Beneficiary added successfully"));

        ResponseEntity<Object> response = beneficiaryController.responseEntity(beneficiaryRequest, principal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Beneficiary added successfully", response.getBody());
    }

    @Test
    public void testGetBeneficiaries() {
        Principal principal = mock(Principal.class);

        when(beneficiaryLogics.getBeneficiaries(principal))
                .thenReturn(ResponseEntity.ok(List.of("Beneficiary1", "Beneficiary2")));

        ResponseEntity<Object> response = beneficiaryController.responseEntity1(principal);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of("Beneficiary1", "Beneficiary2"), response.getBody());
    }
}
