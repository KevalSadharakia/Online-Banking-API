package com.bank.api.logics;

import com.bank.api.dto.TransferRequest;
import com.bank.api.dto.TransferResponse;
import com.bank.api.entity.Account;
import com.bank.api.entity.PersonalDetails;
import com.bank.api.entity.Transaction;
import com.bank.api.services.AccountService;
import com.bank.api.services.PersonalDetailsService;
import com.bank.api.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AccountLogics {
    @Autowired
    AccountService accountService;

    @Autowired
    PersonalDetailsService personalDetailsService;

    @Autowired
    TransactionService transactionService;

    public ResponseEntity<Object> transferMoney(TransferRequest transferRequest, Principal principal){

        PersonalDetails targetAccount = personalDetailsService.getDetailsByAccountNumber(transferRequest.getAccountNumber());
        PersonalDetails currentAccount = (PersonalDetails)(((Authentication)principal).getPrincipal());

        if(targetAccount==null){
            return new ResponseEntity<>("Invalid account number.", HttpStatus.BAD_REQUEST);
        }

        if(targetAccount.getAccountNumber()==currentAccount.getAccountNumber()){
            return new ResponseEntity<>("You can not send money to your own account.", HttpStatus.BAD_REQUEST);
        }

        if(!currentAccount.getAccount().getTransactionPassword().equals(transferRequest.getTransactionPassword())){
            return new ResponseEntity<>("Invalid transaction password.", HttpStatus.BAD_REQUEST);
        }

        if(!targetAccount.getFirstName().equals(transferRequest.getFirstName())){
            return new ResponseEntity<>("Invalid name.", HttpStatus.BAD_REQUEST);
        }
        if(!targetAccount.getLastName().equals(transferRequest.getLastName())){
            return new ResponseEntity<>("Invalid last name.", HttpStatus.BAD_REQUEST);
        }
        if(transferRequest.getAmount()<=0){
            return new ResponseEntity<>("Invalid balance.", HttpStatus.BAD_REQUEST);
        }

        long balance = currentAccount.getAccount().getBalance();
        long tbalance = targetAccount.getAccount().getBalance();

        if(balance<transferRequest.getAmount()){
            return new ResponseEntity<>("Insufficient balance.", HttpStatus.BAD_REQUEST);
        }


        currentAccount.getAccount().setBalance(balance- transferRequest.getAmount());
        targetAccount.getAccount().setBalance(tbalance+ transferRequest.getAmount());

        Transaction transaction = new Transaction();
        transaction.setFromAccountId(currentAccount.getAccountNumber());
        transaction.setToAccountId(targetAccount.getAccountNumber());
        transaction.setAmount(transferRequest.getAmount());
        transaction.setTimestamp(System.currentTimeMillis());

        transaction.getAccounts().add(targetAccount.getAccount());
        transaction.getAccounts().add(currentAccount.getAccount());
        transactionService.updateTransaction(transaction);

        Set<Transaction> list = currentAccount.getAccount().getTransactions();
        if(list==null){
            list = new HashSet<>();
        }
        list.add(transaction);
        currentAccount.getAccount().setTransactions(list);

        Set<Transaction> list1 = targetAccount.getAccount().getTransactions();
        if(list1==null){
            list1 = new HashSet<>();
        }
        list1.add(transaction);
        targetAccount.getAccount().setTransactions(list1);


        accountService.updateAccount(currentAccount.getAccount());
        accountService.updateAccount(targetAccount.getAccount());


        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setAccountNumber(currentAccount.getAccountNumber());
        transferResponse.setBalance(accountService.getAccount(currentAccount.getAccountNumber()).getBalance());

        return new ResponseEntity<>(targetAccount.getAccount(),HttpStatus.OK);

    }

    public ResponseEntity<Object> getAccountTransaction(int id,Principal principal){
        PersonalDetails currentAccount = (PersonalDetails)(((Authentication)principal).getPrincipal());
        Account account = accountService.getAccount(id);
        if(account==null){
            return new ResponseEntity<>("Invalid account number.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("account.getTrans()", HttpStatus.OK);
    }


    public ResponseEntity<Object> getAccount(int id,Principal principal){
        PersonalDetails currentAccount = (PersonalDetails)(((Authentication)principal).getPrincipal());
        Account account = accountService.getAccount(id);
        if(account==null){
              return new ResponseEntity<>("Invalid account number.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }
}
