package com.bank.api.helper;

import com.bank.api.dto.*;
import com.bank.api.entity.Account;
import com.bank.api.entity.PersonalDetails;
import com.bank.api.entity.Transaction;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ModelConverter {

    public static Account getAccountFromEnableNetBankingModel(EnableNetBankingModel enableNetBankingModel){
        Account account = new Account();
        account.setAccountNumber(enableNetBankingModel.getAccountNumber());
        account.setBalance(1000);
        account.setPhoneNumber(enableNetBankingModel.getPhoneNumber());
        account.setTransactionPassword(enableNetBankingModel.getTransactionPassword());
        return account;
    }

    public static PersonalDetails getPersonalDetailsFromPersonalDetailsRequest(PersonalDetailsRequest personalDetailsRequest){
        PersonalDetails personalDetails = new PersonalDetails();
        personalDetails.setFirstName(personalDetailsRequest.getFirstName());
        personalDetails.setLastName(personalDetailsRequest.getLastName());
        personalDetails.setFatherName(personalDetailsRequest.getFatherName());
        personalDetails.setDob(personalDetailsRequest.getDob());
        personalDetails.setAddress(personalDetailsRequest.getAddress());
        personalDetails.setIdentityProofNumber(personalDetailsRequest.getIdentityProofNumber());
        personalDetails.setContactNumber(personalDetailsRequest.getContactNumber());
        personalDetails.setEmail(personalDetailsRequest.getEmail());
        personalDetails.setGender(personalDetailsRequest.getGender());
        Random random = new Random();
        int accountNumber = random.nextInt(0,Integer.MAX_VALUE);
        personalDetails.setAccountNumber(accountNumber);

        return personalDetails;
    }

    public static DetailsResponse getDetailsResponseFromPersonalDetails(PersonalDetails personalDetails){
        DetailsResponse detailsResponse = new DetailsResponse();
        detailsResponse.setAccountNumber(personalDetails.getAccountNumber());
        detailsResponse.setAddress(personalDetails.getAddress());
        detailsResponse.setDob(personalDetails.getDob());
        detailsResponse.setEmail(personalDetails.getEmail());
        detailsResponse.setFatherName(personalDetails.getFatherName());
        detailsResponse.setContactNumber(personalDetails.getContactNumber());
        detailsResponse.setGender(personalDetails.getGender());
        detailsResponse.setFirstName(personalDetails.getFirstName());
        detailsResponse.setLastName(personalDetails.getLastName());
        detailsResponse.setIdentityProofNumber(personalDetails.getIdentityProofNumber());
        return detailsResponse;
    }

    public static List<TransactionResponse> getTransactionResponseListFromTransactionList(List<Transaction> transactions, Principal principal){
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        int accountNumber = ValueExtrecterFromPrinciple.getDetailsFromPrinciple(principal).getAccountNumber();
        for(Transaction transaction : transactions){
            transactionResponses.add(getTransactionResponseFromTransaction(transaction,accountNumber));
        }
        return transactionResponses;
    }

    public static TransactionResponse getTransactionResponseFromTransaction(Transaction transaction,int accountNumber){
        TransactionResponse transactionResponse = new TransactionResponse();
        if(transaction.getFromAccountId()==accountNumber){
            transactionResponse.setAmount(transaction.getAmount()*-1);
            transactionResponse.setAccountNumber(transaction.getToAccountId());
            transactionResponse.setName(transaction.getToName());
        }else {
            transactionResponse.setAccountNumber(transaction.getFromAccountId());
            transactionResponse.setName(transaction.getFromName());
            transactionResponse.setAmount(transaction.getAmount());
        }
        transactionResponse.setTransactionId(transaction.getTransactionId());
        return transactionResponse;
    }


}
