package com.bank.api.services;

import com.bank.api.entity.Transaction;
import com.bank.api.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    public void updateTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }
}
