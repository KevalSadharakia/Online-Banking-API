//package com.bank.api.controllers;
//
//
//import com.bank.api.dto.TransferRequest;
//import com.bank.api.entity.Account;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.persistence.Column;
//import jakarta.validation.constraints.Pattern;
//import jakarta.validation.constraints.Size;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import java.security.Principal;
//
//@ExtendWith(MockitoExtension.class)
//public class AccountControllerTest {
//
//    private MockMvc mockMvc;
//    @Autowired
//    private WebApplicationContext context;
//
//    @BeforeEach
//    private void setUp(){
//        mockMvc= MockMvcBuilders.webAppContextSetup(context).build();
//    }
//
//
//
//
//    @InjectMocks
//    private AccountController accountController;
//
//    @Mock
//    TransferRequest transferRequest ;
//    @Mock
//    Principal principal;
//
//    ObjectMapper objectMapper = new ObjectMapper();
//    @Test
//    void testTransferMoney(){
//        Account account = new Account();
//        account.setAccountNumber(2367);
//        account.setTransactionPassword("abcdef");
//        account.setPhoneNumber("9876598765");
//        account.setBalance(5000);
//        String jsonRequest = objectMapper.writeValueAsString(account);
//        Mockito.when(accountLogics.transferMoney(transferRequest,principal)).thenReturn(ResponseEntity<Object>.ok("Successful"));
//        MvcResult result = mockMvc.perform()
//
//        accountController.transferMoney(transferRequest,principal);
//    }
//
//}

package com.bank.api.controllers;
import com.bank.api.controllers.AccountController;
import com.bank.api.dto.TransferRequest;
import com.bank.api.entity.Account;
import com.bank.api.logics.AccountLogics;
import com.bank.api.services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AccountControllerTest {

    @InjectMocks
    private AccountController accountController;

    @Mock
    private AccountLogics accountLogics;

    @Mock
    private AccountService accountService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testTransferMoney() {
        TransferRequest transferRequest = new TransferRequest();
        Principal principal = mock(Principal.class);

        // Mock the behavior of accountLogics.transferMoney
        when(accountLogics.transferMoney(transferRequest, principal)).thenReturn(ResponseEntity.ok("Transfer successful"));

        ResponseEntity<Object> response = accountController.transferMoney(transferRequest, principal);

        assertEquals(ResponseEntity.ok("Transfer successful"), response);
    }

    @Test
    public void testGetAccountInfo() {
        Principal principal = mock(Principal.class);

        // Mock the behavior of accountLogics.getAccountInfo
        when(accountLogics.getAccountInfo(principal)).thenReturn(ResponseEntity.ok("Account info"));

        ResponseEntity<Object> response = accountController.getAccountInfo(principal);

        assertEquals(ResponseEntity.ok("Account info"), response);
    }

    @Test
    public void testGetTransactions() {
        Principal principal = mock(Principal.class);

        // Mock the behavior of accountLogics.getTransaction
        when(accountLogics.getTransaction(principal)).thenReturn(ResponseEntity.ok("Transactions"));

        ResponseEntity<Object> response = accountController.getTransactions(principal);

        assertEquals(ResponseEntity.ok("Transactions"), response);
    }

    @Test
    public void testUpdate() {
        Account account = new Account();
        Principal principal = mock(Principal.class);

        // Mock the behavior of accountService.updateAccount
        doNothing().when(accountService).updateAccount(account);

        // Mock the behavior of accountService.getAccount
        when(accountService.getAccount(account.getAccountNumber())).thenReturn(account);

        ResponseEntity<Object> response = accountController.update(account, principal);

        assertEquals(ResponseEntity.ok(account), response);
    }

    @Test
    public void testGetAccount() {
        int accountId = 1;
        Principal principal = mock(Principal.class);

        // Mock the behavior of accountLogics.getAccount
        when(accountLogics.getAccount(accountId, principal)).thenReturn(ResponseEntity.ok("Account details"));

        ResponseEntity<Object> response = accountController.getAccount(accountId, principal);

        assertEquals(ResponseEntity.ok("Account details"), response);
    }

    @Test
    public void testGetAccountTransactions() {
        int accountId = 1;
        Principal principal = mock(Principal.class);

        // Mock the behavior of accountLogics.getAccountTransaction
        when(accountLogics.getAccountTransaction(accountId, principal)).thenReturn(ResponseEntity.ok("Account transactions"));

        ResponseEntity<Object> response = accountController.getAccountTransactions(accountId, principal);

        assertEquals(ResponseEntity.ok("Account transactions"), response);
    }



    // Write similar test cases for other controller methods...
}