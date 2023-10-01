package com.bank.api.services;

import com.bank.api.dto.JWTRequest;
import com.bank.api.entity.PersonalDetails;
import com.bank.api.repositories.PersonalDetailsRepository;
import com.bank.api.services.PersonalDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class PersonalDetailsServiceTest {

    @InjectMocks
    private PersonalDetailsService personalDetailsService;

    @Mock
    private PersonalDetailsRepository personalDetailsRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSave() {
        PersonalDetails personalDetails = new PersonalDetails();

        when(personalDetailsRepository.save(personalDetails)).thenReturn(personalDetails);

        Object result = personalDetailsService.save(personalDetails);

        assertNotNull(result);
        assertEquals(personalDetails, result);
    }

    @Test
    public void testIsExist() {
        String email = "test@example.com";

        when(personalDetailsRepository.existsById(email)).thenReturn(true);

        boolean exists = personalDetailsService.isExist(email);

        assertTrue(exists);
    }

    @Test
    public void testGetDetailsByEmail() {
        String email = "test@example.com";
        PersonalDetails personalDetails = new PersonalDetails();
        personalDetails.setEmail(email);

        when(personalDetailsRepository.findById(email)).thenReturn(Optional.of(personalDetails));

        PersonalDetails retrievedDetails = personalDetailsService.getDetailsByEmail(email);

        assertNotNull(retrievedDetails);
        assertEquals(email, retrievedDetails.getEmail());
    }

    @Test
    public void testGetDetailsByEmail_NotFound() {
        String email = "test@example.com";

        when(personalDetailsRepository.findById(email)).thenReturn(Optional.empty());

        PersonalDetails retrievedDetails = personalDetailsService.getDetailsByEmail(email);

        assertNull(retrievedDetails);
    }

}
