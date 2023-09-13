package com.bank.api.helper;

import com.bank.api.dio.EnableNetBankingModel;
import com.bank.api.dio.PersonalDetailsRequest;
import com.bank.api.entity.Account;
import com.bank.api.entity.PersonalDetails;

import java.util.Random;

public class ModelConverter {

    public static Account getAccountFromEnableNetBankingModel(EnableNetBankingModel enableNetBankingModel){
        Account account = new Account();
        account.setAccountNumber(enableNetBankingModel.getAccountNumber());
        account.setBalance(0);
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

}
