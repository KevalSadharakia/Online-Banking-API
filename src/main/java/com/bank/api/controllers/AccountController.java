package com.bank.api.controllers;

import com.bank.api.dto.TransferRequest;
import com.bank.api.dto.TransferResponse;
import com.bank.api.entity.Account;
import com.bank.api.entity.PersonalDetails;
import com.bank.api.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AccountController {

    @Autowired
    AccountService accountService;
    @PostMapping("/transfer")
    public ResponseEntity<Object> transferMoney(@RequestBody TransferRequest transferRequest, Principal principal){
        if(transferRequest.getAmount()<=0){
            return new ResponseEntity<>("Invalid balance.", HttpStatus.BAD_REQUEST);
        }
        PersonalDetails personalDetails = (PersonalDetails)(((Authentication)principal).getPrincipal());
        Account senderAccount = personalDetails.getAccount();
        long balance = senderAccount.getBalance();

        if(balance<transferRequest.getAmount()){
            return new ResponseEntity<>("Insufficient balance.", HttpStatus.BAD_REQUEST);
        }
        accountService.updateBalance(senderAccount.getAccountNumber(),balance- transferRequest.getAmount());
        Account updatedAccount = accountService.getAccount(senderAccount.getAccountNumber());
        if(updatedAccount.getBalance()!=balance- transferRequest.getAmount()){
            return new ResponseEntity<>("Something went wrong 1.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        accountService.updateBalance(transferRequest.getAccountNumber(),balance+ transferRequest.getAmount());
        Account updatedAccount1 = accountService.getAccount(transferRequest.getAccountNumber());

        if(updatedAccount1.getBalance()!=balance+ transferRequest.getAmount()){
            return new ResponseEntity<>("Something went wrong 2.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setAccountNumber(updatedAccount.getAccountNumber());
        transferResponse.setBalance(updatedAccount.getBalance());

        return new ResponseEntity<>(transferResponse,HttpStatus.OK);

    }


}
