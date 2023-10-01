package com.bank.api.services;

import com.bank.api.entity.Transaction;
import com.bank.api.repositories.TransactionRepository;
import com.bank.api.services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    @InjectMocks
    private TransactionService transactionService;

    @Mock
    private TransactionRepository transactionRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateTransaction() {
        Transaction transaction = new Transaction();

        when(transactionRepository.save(transaction)).thenReturn(transaction);

        transactionService.updateTransaction(transaction);

        verify(transactionRepository, times(1)).save(transaction);
    }
}
