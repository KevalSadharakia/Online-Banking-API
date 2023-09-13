package com.bank.api.services;

import com.bank.api.entity.Account;
import com.bank.api.models.JWTRequest;
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
        Optional<Account> optionalAccount= accountRepository.findByUsername(username);
        return optionalAccount.isPresent();
    }
    public Account getAccountByUsername(String username){
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if(!accountOptional.isPresent()){
            return null;
        }
        return accountOptional.get();
    }

    public boolean isValidAccount(JWTRequest jwtRequest){
        Account account = getAccountByUsername(jwtRequest.getUsername());
        if(account==null){
            return false;
        }
        if(account.getAccountPassword().equals(jwtRequest.getPassword())){
            return true;
        }
        return false;
    }

    public boolean isNetBankingAlreadyEnabled(int id){
        Optional<Account> account = accountRepository.findByAccountNumber(id);
        return account.isPresent();
    }


}
