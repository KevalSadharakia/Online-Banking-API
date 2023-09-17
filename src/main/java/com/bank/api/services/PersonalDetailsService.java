package com.bank.api.services;

import com.bank.api.dto.JWTRequest;
import com.bank.api.entity.PersonalDetails;
import com.bank.api.repositories.PersonalDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonalDetailsService {

    @Autowired
    PersonalDetailsRepository personalDetailsRepository;


    public Object save(PersonalDetails personalDetails){
        return personalDetailsRepository.save(personalDetails);
    }

    public boolean isExist(String email){
        return personalDetailsRepository.existsById(email);
    }

    public PersonalDetails getDetailsByAccountNumber(int accountNumber){
        Optional<PersonalDetails> details =personalDetailsRepository.findByAccountNumber(accountNumber);
        if(details.isPresent()){
            return details.get();
        }
        return null;
    }
    public PersonalDetails getDetailsByUsername(String username){
        Optional<PersonalDetails> details =personalDetailsRepository.findByUsername(username);
        if(details.isPresent()){
            return details.get();
        }
        return null;
    }

    public boolean isNetBankingAlreadyEnabled(int accountNumber){
        PersonalDetails personalDetails = getDetailsByAccountNumber(accountNumber);
        if (personalDetails==null){
            return false;
        }
        if(personalDetails.getPassword()==null || personalDetails.getUsername()==null){
            return false;
        }
        return true;
    }


    public boolean isLoginCredentialValid(JWTRequest jwtRequest){
        Optional<PersonalDetails> personalDetailsOptional = personalDetailsRepository.findByUsername(jwtRequest.getUsername());
        if(personalDetailsOptional.isPresent()){
            PersonalDetails personalDetails = personalDetailsOptional.get();
            if(personalDetails.getPassword().equals(jwtRequest.getPassword())){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }


}
