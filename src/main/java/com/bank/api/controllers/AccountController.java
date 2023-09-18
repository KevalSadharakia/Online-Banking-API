package com.bank.api.controllers;

import com.bank.api.dto.TransferRequest;
import com.bank.api.dto.TransferResponse;
import com.bank.api.entity.Account;
import com.bank.api.entity.PersonalDetails;
import com.bank.api.logics.AccountLogics;
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
@RequestMapping("/api/account")
public class AccountController {

    @Autowired
    AccountLogics accountLogics;

    @Autowired
    AccountService accountService;

    @PostMapping("/transfer")
    public ResponseEntity<Object> transferMoney(@RequestBody TransferRequest transferRequest, Principal principal){
        return accountLogics.transferMoney(transferRequest,principal);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAccount(@PathVariable int id, Principal principal){
        return accountLogics.getAccount(id,principal);
    }

    @GetMapping()
    public ResponseEntity<Object> getAccountInfo(Principal principal){
        return accountLogics.getAccountInfo(principal);
    }


    @GetMapping("/{id}/transactions")
    public ResponseEntity<Object> getAccountTransactions(@PathVariable int id, Principal principal){
        return accountLogics.getAccountTransaction(id,principal);
    }

    @PutMapping()
    public ResponseEntity<Object> update(@RequestBody Account account, Principal principal){
        accountService.updateAccount(account);
        return new ResponseEntity<>(accountService.getAccount(account.getAccountNumber()),HttpStatus.OK);
    }



}
