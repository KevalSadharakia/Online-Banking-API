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

    public static PersonalDetails getPersonalDetailsFromPersonalDetailsRequest(PersonalDetailsRequest personalDetailsRequest,long count){
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
        if(count==0){
            personalDetails.setRole("ADMIN");
            personalDetails.setActive(true);
            personalDetails.setAccepted(true);
        }else {
            personalDetails.setRole("USER");
            personalDetails.setActive(false);
            personalDetails.setAccepted(null);
        }



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
        if(personalDetails.getAccount()!=null)detailsResponse.setBalance(personalDetails.getAccount().getBalance());
        detailsResponse.setUsername(personalDetails.getUsername());
        if(personalDetails.getRole()!=null && personalDetails.getRole().equals("ADMIN")){
            detailsResponse.setAdmin(true);
        }else {
            detailsResponse.setAdmin(false);
        }
        if(personalDetails.getAccepted()==null){
            detailsResponse.setAccepted("Pending");
        }else if(personalDetails.getAccepted()==true){
            detailsResponse.setAccepted("Accepted");
        }else if(personalDetails.getAccepted()==false){
            detailsResponse.setAccepted("Rejected");
        }

        detailsResponse.setActive(personalDetails.getActive());

        return detailsResponse;
    }

    public static List<TransactionResponse> getTransactionResponseListFromTransactionList(List<Transaction> transactions, Principal principal){
        List<TransactionResponse> transactionResponses = new ArrayList<>();
        int accountNumber = ValueExtrecterFromPrinciple.getDetailsFromPrinciple(principal).getAccountNumber();
        for(int i =transactions.size()-1;i>=0;i--){
            transactionResponses.add(getTransactionResponseFromTransaction(transactions.get(i),accountNumber));
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
        transactionResponse.setTimestamp(transaction.getTimestamp());
        transactionResponse.setTransactionId(transaction.getTransactionId());
        return transactionResponse;
    }


}
