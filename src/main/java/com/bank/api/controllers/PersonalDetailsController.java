package com.bank.api.controllers;

import com.bank.api.models.PersonalDetails;
import com.bank.api.models.PersonalDetailsRequest;
import com.bank.api.services.PersonalDetailsService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Random;

@RestController
@CrossOrigin
@RequestMapping("/api/createAccount")
public class PersonalDetailsController {
    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    PersonalDetailsService personalDetailsService;
    @PostMapping
    public ResponseEntity<Object> createAccount(@Valid @RequestBody PersonalDetailsRequest personalDetailsRequest){

        PersonalDetails personalDetails = new PersonalDetails();
        personalDetails.setFirstName(personalDetailsRequest.getFirstName());
        personalDetails.setLastName(personalDetailsRequest.getLastName());
        personalDetails.setFatherName(personalDetailsRequest.getFatherName());
        personalDetails.setDob(personalDetailsRequest.getDob());
        personalDetails.setAddress(personalDetailsRequest.getAddress());
        personalDetails.setIdentityProofNumber(personalDetailsRequest.getIdentityProofNumber());
        personalDetails.setContactNumber(personalDetailsRequest.getContactNumber());
        personalDetails.setEmail(personalDetailsRequest.getEmail());
        personalDetails.setGender(personalDetailsRequest.getGender());

        Random random = new Random();
        int accountNumber = random.nextInt(0,Integer.MAX_VALUE);
        personalDetails.setAccountNumber(accountNumber);

       return new ResponseEntity<>(personalDetailsService.createAccount(personalDetails), HttpStatus.CREATED) ;


    }

}
