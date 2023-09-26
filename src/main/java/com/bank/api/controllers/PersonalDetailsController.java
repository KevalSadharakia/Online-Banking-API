package com.bank.api.controllers;

import com.bank.api.dto.*;
import com.bank.api.entity.Beneficiary;
import com.bank.api.entity.PersonalDetails;
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
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping("/updatePassword")
    public ResponseEntity<Object> updatePassword(@Valid @RequestBody UpdatePassword updatePassword,Principal principal){

        PersonalDetails personalDetails = ValueExtrecterFromPrinciple.getDetailsFromPrinciple(principal);
        personalDetails = personalDetailsService.getDetailsByEmail(personalDetails.getEmail());

        if(!personalDetails.getPassword().equals(updatePassword.getCurrentPassword())){
            return new ResponseEntity<>("Wrong password", HttpStatus.BAD_REQUEST);
        }
        if(updatePassword.getNewPassword().length()<4){
            return new ResponseEntity<>("Password must be greater than 3 digit", HttpStatus.BAD_REQUEST);
        }

        personalDetails.setPassword(updatePassword.getNewPassword());
        personalDetailsService.save(personalDetails);


        return new ResponseEntity<>("Password is updated.", HttpStatus.OK);
    }

    @PostMapping("/username")
    public ResponseEntity<Object> getUsername(@Valid @RequestBody ForgotUsernameRequest forgotUsernameRequest){


        PersonalDetails personalDetails = personalDetailsService.getDetailsByAccountNumber(forgotUsernameRequest.getAccountNumber());

        if(personalDetails==null){
            return new ResponseEntity<>("No account found!", HttpStatus.BAD_REQUEST);
        }
        if(!personalDetails.getIdentityProofNumber().equals(forgotUsernameRequest.getAadharNumber())){
            return new ResponseEntity<>("Wrong aadhar number", HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>("Your username is "+personalDetails.getUsername(), HttpStatus.OK);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<Object> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotPasswordRequest){

        PersonalDetails personalDetails = personalDetailsService.getDetailsByAccountNumber(forgotPasswordRequest.getAccountNumber());
        if(personalDetails==null){
            return new ResponseEntity<>("No account found!", HttpStatus.BAD_REQUEST);
        }
        if(!personalDetails.getIdentityProofNumber().equals(forgotPasswordRequest.getAadharNumber())){
            return new ResponseEntity<>("Wrong aadhar number", HttpStatus.BAD_REQUEST);
        }
        if(!personalDetails.getUsername().equals(forgotPasswordRequest.getUserId())){
            return new ResponseEntity<>("Wrong User ID", HttpStatus.BAD_REQUEST);
        }

        personalDetails.setPassword(forgotPasswordRequest.getNewPassword());
        personalDetailsService.save(personalDetails);
        return new ResponseEntity<>("Password is updated.", HttpStatus.OK);

    }


    @GetMapping("/details")
    public ResponseEntity<Object> getDetails(Principal principal){
        PersonalDetails personalDetails = personalDetailsService.getDetailsByEmail(ValueExtrecterFromPrinciple.getDetailsFromPrinciple(principal).getEmail());
        if(personalDetails==null){
            return new ResponseEntity<>("Account Not Found",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ModelConverter.getDetailsResponseFromPersonalDetails(personalDetails),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/details/{id}")
    public ResponseEntity<Object> getDetailsOfAPerson(@PathVariable int id, Principal principal){
        PersonalDetails personalDetails = personalDetailsService.getDetailsByAccountNumber(id);
        if(personalDetails==null){
            return new ResponseEntity<>("Account Not Found",HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(ModelConverter.getDetailsResponseFromPersonalDetails(personalDetails),HttpStatus.OK);
    }


}
