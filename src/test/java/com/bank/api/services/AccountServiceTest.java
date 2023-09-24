package com.bank.api.services;


import com.bank.api.entity.Account;
import com.bank.api.repositories.AccountRepository;
import com.bank.api.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @InjectMocks
    private AccountService accountService;

    @Mock
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateAccount() {
        Account account = new Account();
        account.setAccountNumber(123);

        when(accountRepository.save(account)).thenReturn(account);

        assertDoesNotThrow(() -> accountService.updateAccount(account));
    }

    @Test
    public void testUpdateAccount_NullAccount() {
        assertThrows(RuntimeException.class, () -> accountService.updateAccount(null));
    }

    @Test
    public void testGetAccount() {
        int accountNumber = 123;
        Account account = new Account();
        account.setAccountNumber(accountNumber);

        when(accountRepository.findById(accountNumber)).thenReturn(Optional.of(account));

        Account retrievedAccount = accountService.getAccount(accountNumber);

        assertNotNull(retrievedAccount);
        assertEquals(accountNumber, retrievedAccount.getAccountNumber());
    }

    @Test
    public void testGetAccount_NotFound() {
        int accountNumber = 123;

        when(accountRepository.findById(accountNumber)).thenReturn(Optional.empty());

        Account retrievedAccount = accountService.getAccount(accountNumber);

        assertNull(retrievedAccount);
    }
}