package com.bank.api.services;

import com.bank.api.models.Account;
import com.bank.api.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;
    public Object enableNetBanking(Account account){
        return accountRepository.save(account);
    }

    public boolean isAccountExist(String username){
        return accountRepository.existsById(username);
    }

    public Object isNetBankingAlreadyEnabled(int id){
        Optional<Account> account = accountRepository.findByAccountNumber(id);
        return account.isPresent();
    }


}
