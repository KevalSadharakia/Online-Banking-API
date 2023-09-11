package com.bank.api.repositories;

import com.bank.api.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface AccountRepository extends JpaRepository<Account,String> {

    Optional<Account> findByAccountNumber(int number);

}
