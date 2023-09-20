package com.bank.api.controllers;

import com.bank.api.dto.DepositRequest;
import com.bank.api.dto.TransferRequest;
import com.bank.api.dto.TransferResponse;
import com.bank.api.entity.Account;
import com.bank.api.entity.PersonalDetails;
import com.bank.api.entity.Transaction;
import com.bank.api.helper.ModelConverter;
import com.bank.api.helper.ValueExtrecterFromPrinciple;
import com.bank.api.logics.AccountLogics;
import com.bank.api.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@CrossOrigin
@RequestMapping("/api/account")
public class AccountController {
    private final Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    AccountLogics accountLogics;

    @Autowired
    AccountService accountService;

    @PostMapping("/transfer")
    public ResponseEntity<Object> transferMoney(@RequestBody TransferRequest transferRequest, Principal principal){
        return accountLogics.transferMoney(transferRequest,principal);
    }

    @GetMapping
    public ResponseEntity<Object> getAccountInfo(Principal principal){
        return accountLogics.getAccountInfo(principal);
    }


    @GetMapping("/transactions")
    public ResponseEntity<Object> getTransactions(Principal principal){
        return accountLogics.getTransaction(principal);
    }


    @PutMapping
    public ResponseEntity<Object> update(@RequestBody Account account, Principal principal){
        accountService.updateAccount(account);
        return new ResponseEntity<>(accountService.getAccount(account.getAccountNumber()),HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getAccount(@PathVariable int id, Principal principal){
        return accountLogics.getAccount(id,principal);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/{id}/transactions")
    public ResponseEntity<Object> getAccountTransactions(@PathVariable int id, Principal principal){
        return accountLogics.getAccountTransaction(id,principal);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/withdraw")
    public ResponseEntity<Object> withdraw(@RequestBody DepositRequest depositRequest, Principal principal) {
        return accountLogics.withdrawMoney(depositRequest,principal);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/deposit")
    public ResponseEntity<Object> deposit(@RequestBody DepositRequest depositRequest, Principal principal) {
        return accountLogics.depositMoney(depositRequest,principal);
    }


}
