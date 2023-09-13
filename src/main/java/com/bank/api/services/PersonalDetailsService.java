package com.bank.api.services;

import com.bank.api.entity.PersonalDetails;
import com.bank.api.repositories.PersonalDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonalDetailsService {

    @Autowired
    PersonalDetailsRepository personalDetailsRepository;
    public Object createAccount(PersonalDetails personalDetails){
        return personalDetailsRepository.save(personalDetails);
    }

    public Object save(PersonalDetails personalDetails){
        return personalDetailsRepository.save(personalDetails);
    }

    public PersonalDetails getDetailsByAccountNumber(int accountNumber){
        Optional<PersonalDetails> details =personalDetailsRepository.findByAccountNumber(accountNumber);
        try {
            if(details==null) {
                return null;
            }else {
                return details.get();
            }
        }catch (Exception e){
            return null;
        }

    }
}
