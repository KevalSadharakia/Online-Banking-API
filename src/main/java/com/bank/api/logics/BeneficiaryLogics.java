package com.bank.api.logics;

import com.bank.api.entity.Account;
import com.bank.api.entity.Beneficiary;
import com.bank.api.entity.PersonalDetails;
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
    PersonalDetailsService personalDetailsService;

    @Autowired
    BeneficiaryService beneficiaryService;

    public ResponseEntity<Object> addBeneficiary(Beneficiary beneficiary, Principal principal){
        PersonalDetails personalDetails = ValueExtrecterFromPrinciple.getDetailsFromPrinciple(principal);
        PersonalDetails personalDetails1 =  personalDetailsService.getDetailsByAccountNumber(beneficiary.getAccountNumber());
        if(personalDetails1==null){
            return new ResponseEntity<>("Invalid account number", HttpStatus.BAD_REQUEST);
        }
        if(!personalDetails1.getFirstName().equals(beneficiary.getFirstName()) || !personalDetails1.getLastName().equals(beneficiary.getLastName())){
            return new ResponseEntity<>("Name not matched", HttpStatus.BAD_REQUEST);
        }
        List<Beneficiary> list = personalDetails.getBeneficiaries();
        for(int i =0;i<list.size();i++){
            if(list.get(i).getAccountNumber()== beneficiary.getAccountNumber()){
                return new ResponseEntity<>("Account already present", HttpStatus.BAD_REQUEST);
            }
            break;
        }

        personalDetails.getBeneficiaries().add(beneficiary);
        personalDetailsService.save(personalDetails);

        return new ResponseEntity<>("Saved successfully", HttpStatus.OK);
    }

}
