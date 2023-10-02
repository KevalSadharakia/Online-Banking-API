package com.bank.api.logics;

import com.bank.api.dto.*;
import com.bank.api.entity.Account;
import com.bank.api.entity.PersonalDetails;
import com.bank.api.entity.Transaction;
import com.bank.api.helper.ModelConverter;
import com.bank.api.helper.ValueExtrecterFromPrinciple;
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

    public ResponseEntity<Object> transferMoney(TransferRequest transferRequest, Principal principal) {

        PersonalDetails targetAccount = personalDetailsService.getDetailsByAccountNumber(transferRequest.getAccountNumber());
        PersonalDetails currentAccount = (PersonalDetails) (((Authentication) principal).getPrincipal());

        if (targetAccount == null) {
            return new ResponseEntity<>("Invalid account number.", HttpStatus.BAD_REQUEST);
        }

        if (targetAccount.getAccountNumber() == currentAccount.getAccountNumber()) {
            return new ResponseEntity<>("You can not send money to your own account.", HttpStatus.BAD_REQUEST);
        }

        if (!currentAccount.getAccount().getTransactionPassword().equals(transferRequest.getTransactionPassword())) {
            return new ResponseEntity<>("Invalid transaction password.", HttpStatus.BAD_REQUEST);
        }

        if (!targetAccount.getFirstName().equals(transferRequest.getFirstName())) {
            return new ResponseEntity<>("Invalid name.", HttpStatus.BAD_REQUEST);
        }
        if (!targetAccount.getLastName().equals(transferRequest.getLastName())) {
            return new ResponseEntity<>("Invalid last name.", HttpStatus.BAD_REQUEST);
        }
        if (transferRequest.getAmount() <= 0) {
            return new ResponseEntity<>("Invalid balance.", HttpStatus.BAD_REQUEST);
        }

        long balance = currentAccount.getAccount().getBalance();
        long tbalance = targetAccount.getAccount().getBalance();

        if (balance < transferRequest.getAmount()) {
            return new ResponseEntity<>("Insufficient balance.", HttpStatus.BAD_REQUEST);
        }


        currentAccount.getAccount().setBalance(balance - transferRequest.getAmount());
        targetAccount.getAccount().setBalance(tbalance + transferRequest.getAmount());

        Transaction transaction = new Transaction();
        transaction.setFromAccountId(currentAccount.getAccountNumber());
        transaction.setFromName(currentAccount.getFirstName() + " " + currentAccount.getLastName());
        transaction.setToAccountId(targetAccount.getAccountNumber());
        transaction.setToName(targetAccount.getFirstName() + " " + targetAccount.getLastName());
        transaction.setAmount(transferRequest.getAmount());
        transaction.setTimestamp(System.currentTimeMillis());
        transaction.getAccounts().add(targetAccount.getAccount());
        transaction.getAccounts().add(currentAccount.getAccount());

        transactionService.updateTransaction(transaction);

        List<Transaction> list = currentAccount.getAccount().getTransactions();
        list.add(transaction);
        currentAccount.getAccount().setTransactions(list);

        List<Transaction> list1 = targetAccount.getAccount().getTransactions();

        list1.add(transaction);
        targetAccount.getAccount().setTransactions(list1);


        accountService.updateAccount(currentAccount.getAccount());
        accountService.updateAccount(targetAccount.getAccount());


        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setAccountNumber(currentAccount.getAccountNumber());
        transferResponse.setBalance(accountService.getAccount(currentAccount.getAccountNumber()).getBalance());

        return new ResponseEntity<>(targetAccount.getAccount(), HttpStatus.OK);

    }

    public ResponseEntity<Object> getAccountTransaction(int id, Principal principal) {
        PersonalDetails currentAccount = (PersonalDetails) (((Authentication) principal).getPrincipal());
        Account account = accountService.getAccount(id);
        if (account == null) {
            return new ResponseEntity<>("Invalid account number.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("account.getTrans()", HttpStatus.OK);
    }

    public ResponseEntity<Object> getTransaction(Principal principal) {
        int accountNumber = ValueExtrecterFromPrinciple.getDetailsFromPrinciple(principal).getAccountNumber();
        List<Transaction> list = accountService.getAccount(accountNumber).getTransactions();

        List<TransactionResponse> list1 = ModelConverter.getTransactionResponseListFromTransactionList(list, principal);

        return new ResponseEntity<>(list1, HttpStatus.OK);
    }

    public ResponseEntity<Object> getAccountInfo(Principal principal) {
        PersonalDetails currentAccount = (PersonalDetails) (((Authentication) principal).getPrincipal());
        Account account = accountService.getAccount(currentAccount.getAccountNumber());
        if (account == null) {
            return new ResponseEntity<>("Invalid account number.", HttpStatus.BAD_REQUEST);
        }
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setAccountNumber(account.getAccountNumber());
        accountResponse.setBalance(account.getBalance());

        if(currentAccount.getRole()!=null && currentAccount.getRole().equals("ADMIN")){
            accountResponse.setAdmin(true);
        }else {
            accountResponse.setAdmin(false);
        }
        return new ResponseEntity<>(accountResponse, HttpStatus.OK);
    }

    public ResponseEntity<Object> getAccount(int id, Principal principal) {
        Account account = accountService.getAccount(id);
        if (account == null) {
            return new ResponseEntity<>("Invalid account number.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    public ResponseEntity<Object> depositMoney(DepositRequest depositRequest, Principal principal) {
        PersonalDetails personalDetails = personalDetailsService.getDetailsByAccountNumber(depositRequest.getAccountNumber());
        if(personalDetails==null){
            return new ResponseEntity<>("No account found",HttpStatus.BAD_REQUEST);
        }
        if(!personalDetails.getFirstName().equals(depositRequest.getFirstName()) || !personalDetails.getLastName().equals(depositRequest.getLastName()) ){
            return new ResponseEntity<>("Invalid name",HttpStatus.BAD_REQUEST);
        }
        Account account = accountService.getAccount(depositRequest.getAccountNumber());
        long amount = account.getBalance()+depositRequest.getAmount();
        account.setBalance(amount);
        accountService.updateAccount(account);

        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setAccountNumber(account.getAccountNumber());
        transferResponse.setBalance(accountService.getAccount(account.getAccountNumber()).getBalance());

        Transaction transaction = new Transaction();
        transaction.setFromAccountId(depositRequest.getAccountNumber()+1);
        transaction.setFromName("Deposit");
        transaction.setToAccountId(depositRequest.getAccountNumber());
        transaction.setAmount(depositRequest.getAmount());
        transaction.setTimestamp(System.currentTimeMillis());

        transactionService.updateTransaction(transaction);
        account.getTransactions().add(transaction);
        accountService.updateAccount(account);


        return new ResponseEntity<>(transferResponse,HttpStatus.OK);
    }

    public ResponseEntity<Object> withdrawMoney(DepositRequest depositRequest, Principal principal) {
        PersonalDetails personalDetails = personalDetailsService.getDetailsByAccountNumber(depositRequest.getAccountNumber());
        if(personalDetails==null){
            return new ResponseEntity<>("No account found",HttpStatus.BAD_REQUEST);
        }
        if(!personalDetails.getFirstName().equals(depositRequest.getFirstName()) || !personalDetails.getLastName().equals(depositRequest.getLastName()) ){
            return new ResponseEntity<>("Invalid name",HttpStatus.BAD_REQUEST);
        }

        Account account = accountService.getAccount(depositRequest.getAccountNumber());
        long amount = account.getBalance()-depositRequest.getAmount();
        if(amount<0){
            return new ResponseEntity<>("Insufficient balance",HttpStatus.BAD_REQUEST);
        }

        account.setBalance(amount);
        accountService.updateAccount(account);

        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setAccountNumber(account.getAccountNumber());
        transferResponse.setBalance(accountService.getAccount(account.getAccountNumber()).getBalance());

        Transaction transaction = new Transaction();
        transaction.setToAccountId(depositRequest.getAccountNumber()+1);
        transaction.setToName("Withdraw");
        transaction.setFromAccountId(depositRequest.getAccountNumber());
        transaction.setAmount(depositRequest.getAmount());
        transaction.setTimestamp(System.currentTimeMillis());

        transactionService.updateTransaction(transaction);
        account.getTransactions().add(transaction);
        accountService.updateAccount(account);

        return new ResponseEntity<>(transferResponse,HttpStatus.OK);
    }
}
