package com.bank.api.logics;

import com.bank.api.dto.BeneficiaryRequest;
import com.bank.api.entity.Account;
import com.bank.api.entity.Beneficiary;
import com.bank.api.entity.PersonalDetails;
import com.bank.api.helper.ModelConverter;
import com.bank.api.helper.ValueExtrecterFromPrinciple;
import com.bank.api.services.AccountService;
import com.bank.api.services.BeneficiaryService;
import com.bank.api.services.PersonalDetailsService;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class BeneficiaryLogics {

    @Autowired
    BeneficiaryService beneficiaryService;

    @Autowired
    PersonalDetailsService personalDetailsService;

    public ResponseEntity<Object> addBeneficiary(BeneficiaryRequest beneficiaryRequest, Principal principal){

        PersonalDetails personalDetails =  personalDetailsService.getDetailsByAccountNumber(beneficiaryRequest.getAccountNumber());
        PersonalDetails personalDetails1 = ValueExtrecterFromPrinciple.getDetailsFromPrinciple(principal);

        if(personalDetails==null){
            return new ResponseEntity<>("Invalid account number", HttpStatus.BAD_REQUEST);
        }
        System.out.println(personalDetails.getFirstName());
        System.out.println(beneficiaryRequest.getFirstName());

        System.out.println(personalDetails.getLastName());
        System.out.println(beneficiaryRequest.getLastName());

        if(!personalDetails.getFirstName().equals(beneficiaryRequest.getFirstName()) || !personalDetails.getLastName().equals(beneficiaryRequest.getLastName())){
            return new ResponseEntity<>("Name not matched", HttpStatus.BAD_REQUEST);
        }

        List<Beneficiary> list = beneficiaryService.getBeneficiaryList(personalDetails1.getEmail());
        for(int i =0;i<list.size();i++){
            if(list.get(i).getAccountNumber()== beneficiaryRequest.getAccountNumber()){
                return new ResponseEntity<>("Account already present", HttpStatus.BAD_REQUEST);
            }
        }
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setAccountNumber(beneficiaryRequest.getAccountNumber());
        beneficiary.setFirstName(beneficiaryRequest.getFirstName());
        beneficiary.setLastName(beneficiaryRequest.getLastName());
        beneficiary.setAddedByEmail(personalDetails1.getEmail());

        return new ResponseEntity<>(beneficiaryService.add(beneficiary), HttpStatus.OK);
    }


//    public ResponseEntity<Object> getBeneficiaryList(Principal principal){
//        PersonalDetails personalDetails = ValueExtrecterFromPrinciple.getDetailsFromPrinciple(principal);
//
//        return new ResponseEntity<>(list,HttpStatus.OK);
//    }
//
    public ResponseEntity<Object> getBeneficiaries(Principal principal){
        PersonalDetails personalDetails = ValueExtrecterFromPrinciple.getDetailsFromPrinciple(principal);
        List<Beneficiary> list = beneficiaryService.getBeneficiaryList(personalDetails.getEmail());
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

}
