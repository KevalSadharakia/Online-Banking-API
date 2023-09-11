package com.bank.api.services;

import com.bank.api.models.PersonalDetails;
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

    public PersonalDetails getDetails(int accountNumber){
        Optional<PersonalDetails> details =personalDetailsRepository.findById(accountNumber);
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
