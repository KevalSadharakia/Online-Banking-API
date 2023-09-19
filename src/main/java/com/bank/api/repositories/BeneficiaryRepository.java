package com.bank.api.repositories;

import com.bank.api.entity.Account;
import com.bank.api.entity.Beneficiary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary,Integer> {
}
