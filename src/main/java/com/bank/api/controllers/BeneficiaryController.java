package com.bank.api.controllers;

import com.bank.api.dto.BeneficiaryRequest;
import com.bank.api.entity.PersonalDetails;
import com.bank.api.helper.ValueExtrecterFromPrinciple;
import com.bank.api.logics.BeneficiaryLogics;
import com.bank.api.services.BeneficiaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.OncePerRequestFilter;

import java.security.Principal;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class BeneficiaryController {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    BeneficiaryLogics beneficiaryLogics;
    @PostMapping("/addBeneficiary")
    public ResponseEntity<Object> responseEntity(@RequestBody BeneficiaryRequest beneficiary, Principal principal){
        return beneficiaryLogics.addBeneficiary(beneficiary,principal);
    }
    @GetMapping("/beneficiaries")
    public ResponseEntity<Object> responseEntity1(Principal principal){
        return beneficiaryLogics.getBeneficiaries(principal);
    }

}
