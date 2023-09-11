package com.bank.api.controllers;

import com.bank.api.models.Account;
import com.bank.api.models.PersonalDetails;
import com.bank.api.services.AccountService;
import com.bank.api.services.PersonalDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    PersonalDetailsService personalDetailsService;

    @PostMapping("/enableNetBanking")
    public ResponseEntity<Object> enableNetBanking(@Valid @RequestBody Account account){

        PersonalDetails personalDetails = personalDetailsService.getDetails(account.getAccountNumber());

        if(accountService.isAccountExist(account.getUsername())){
            return new ResponseEntity<>("Username is already used",HttpStatus.OK);
        }

        if(accountService.isAccountExist(account.getUsername())){
            return new ResponseEntity<>("User name already in use",HttpStatus.BAD_REQUEST);
        }



        if(personalDetails==null){
            return new ResponseEntity<>("Invalid account number",HttpStatus.NOT_FOUND);
        }else {
           if(!personalDetails.getContactNumber().equals(account.getPhoneNumber())){
               return new ResponseEntity<>("Invalid phone number",HttpStatus.NOT_FOUND);
           }
        }

        return new ResponseEntity<>(accountService.enableNetBanking(account), HttpStatus.OK);
    }


}
