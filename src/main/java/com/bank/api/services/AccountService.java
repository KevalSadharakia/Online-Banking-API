package com.bank.api.services;

import com.bank.api.models.Account;
import com.bank.api.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;
    public Object enableNetBanking(Account account){
        return accountRepository.save(account);
    }
}
