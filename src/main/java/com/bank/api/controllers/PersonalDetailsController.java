package com.bank.api.controllers;

import com.bank.api.dto.BeneficiaryRequest;
import com.bank.api.dto.EnableNetBankingModel;
import com.bank.api.entity.Beneficiary;
import com.bank.api.entity.PersonalDetails;
import com.bank.api.dto.PersonalDetailsRequest;
import com.bank.api.helper.ModelConverter;
import com.bank.api.helper.ValueExtrecterFromPrinciple;
import com.bank.api.logics.BeneficiaryLogics;
import com.bank.api.services.BeneficiaryService;
import com.bank.api.services.PersonalDetailsService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.OncePerRequestFilter;

import java.security.Principal;


@RestController
@RequestMapping("/api")
@CrossOrigin
public class PersonalDetailsController {
    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

    @Autowired
    PersonalDetailsService personalDetailsService;



    @PostMapping("/createAccount")
    public ResponseEntity<Object> createAccount(@Valid @RequestBody PersonalDetailsRequest personalDetailsRequest){

        if(personalDetailsService.isExist(personalDetailsRequest.getEmail())){
            return new ResponseEntity<>("Email address is used", HttpStatus.BAD_REQUEST) ;
        }
        return new ResponseEntity<>(personalDetailsService.save(ModelConverter.getPersonalDetailsFromPersonalDetailsRequest(personalDetailsRequest)), HttpStatus.CREATED) ;

    }


    @PostMapping("/enableNetBanking")
    public ResponseEntity<Object> enableNetBanking(@Valid @RequestBody EnableNetBankingModel enableNetBankingModel){

        PersonalDetails personalDetails = personalDetailsService.getDetailsByAccountNumber(enableNetBankingModel.getAccountNumber());
        if(personalDetailsService.isNetBankingAlreadyEnabled(enableNetBankingModel.getAccountNumber())){
            return new ResponseEntity<>("Account already created",HttpStatus.BAD_REQUEST);
        }

        if(personalDetails==null){
            return new ResponseEntity<>("Invalid account number",HttpStatus.BAD_REQUEST);
        }else {
            if(!personalDetails.getContactNumber().equals(enableNetBankingModel.getPhoneNumber())){
                return new ResponseEntity<>("Invalid phone number",HttpStatus.BAD_REQUEST);
            }
        }
        personalDetails.setPassword(enableNetBankingModel.getAccountPassword());
        personalDetails.setUsername(enableNetBankingModel.getUsername());

        personalDetails.setAccount(ModelConverter.getAccountFromEnableNetBankingModel(enableNetBankingModel));

        return new ResponseEntity<>(personalDetailsService.save(personalDetails), HttpStatus.OK);
    }

    @GetMapping("/details")
    public ResponseEntity<Object> getDetails(Principal principal){
        PersonalDetails personalDetails = personalDetailsService.getDetailsByEmail(ValueExtrecterFromPrinciple.getDetailsFromPrinciple(principal).getEmail());
        if(personalDetails==null){
            return new ResponseEntity<>("Account Not Found",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ModelConverter.getDetailsResponseFromPersonalDetails(personalDetails),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAccount(@PathVariable int id, Principal principal){
        return new ResponseEntity<>(personalDetailsService.getDetailsByAccountNumber(id),HttpStatus.OK);
    }

}
