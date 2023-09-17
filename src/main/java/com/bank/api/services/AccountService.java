package com.bank.api.services;

import com.bank.api.entity.Account;
import com.bank.api.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;


    public void updateAccount(Account account){
        if(account==null) throw new RuntimeException("Account not found ");
        accountRepository.save(account);
    }

    public Account getAccount(int accountNumber){
        Optional<Account> accountOptional = accountRepository.findById(accountNumber);
        if(accountOptional.isPresent()){
            return accountOptional.get();
        }
        return null;
    }


}
