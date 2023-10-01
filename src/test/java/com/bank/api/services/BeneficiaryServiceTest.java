package com.bank.api.services;

import com.bank.api.entity.Beneficiary;
import com.bank.api.repositories.BeneficiaryRepository;
import com.bank.api.services.BeneficiaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BeneficiaryServiceTest {

    @InjectMocks
    private BeneficiaryService beneficiaryService;

    @Mock
    private BeneficiaryRepository beneficiaryRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAddBeneficiary() {
        Beneficiary beneficiary = new Beneficiary();

        when(beneficiaryRepository.save(beneficiary)).thenReturn(beneficiary);

        Object result = beneficiaryService.add(beneficiary);

        assertNotNull(result);
        assertEquals(beneficiary, result);
    }

    @Test
    public void testGetBeneficiaryList() {
        String email = "test@example.com";
        Beneficiary beneficiary1 = new Beneficiary();
        Beneficiary beneficiary2 = new Beneficiary();

        when(beneficiaryRepository.findAllByAddedByEmail(email)).thenReturn(Arrays.asList(beneficiary1, beneficiary2));

        List<Beneficiary> beneficiaries = beneficiaryService.getBeneficiaryList(email);

        assertNotNull(beneficiaries);
        assertEquals(2, beneficiaries.size());
        assertTrue(beneficiaries.contains(beneficiary1));
        assertTrue(beneficiaries.contains(beneficiary2));
    }

    @Test
    public void testGetBeneficiaryList_Empty() {
        String email = "test@example.com";

        when(beneficiaryRepository.findAllByAddedByEmail(email)).thenReturn(Arrays.asList());

        List<Beneficiary> beneficiaries = beneficiaryService.getBeneficiaryList(email);

        assertNotNull(beneficiaries);
        assertEquals(0, beneficiaries.size());
    }
}