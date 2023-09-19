package com.bank.api.services;

import com.bank.api.entity.Beneficiary;
import com.bank.api.repositories.BeneficiaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Service
public class BeneficiaryService {

    @Autowired
    BeneficiaryRepository beneficiaryRepository;

    @Autowired
    PersonalDetailsService personalDetailsService;

    public void add(Beneficiary beneficiary){
       beneficiaryRepository.save(beneficiary);
    }

    public List<Beneficiary> getBeneficiaryList(String email){
        return null;
    }


}
