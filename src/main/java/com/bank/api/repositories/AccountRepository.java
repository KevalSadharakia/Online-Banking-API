package com.bank.api.repositories;

import com.bank.api.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AccountRepository extends JpaRepository<Account,Integer> {

}
