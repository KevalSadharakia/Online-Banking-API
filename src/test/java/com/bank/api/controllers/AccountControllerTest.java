package com.bank.api.controllers;


import com.bank.api.dto.TransferRequest;
import com.bank.api.entity.Account;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.security.Principal;

@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    private void setUp(){
        mockMvc= MockMvcBuilders.webAppContextSetup(context).build();
    }




    @InjectMocks
    private AccountController accountController;

    @Mock
    TransferRequest transferRequest ;
    @Mock
    Principal principal;

    ObjectMapper objectMapper = new ObjectMapper();
    @Test
    void testTransferMoney(){
        Account account = new Account();
        account.setAccountNumber(2367);
        account.setTransactionPassword("abcdef");
        account.setPhoneNumber("9876598765");
        account.setBalance(5000);
        String jsonRequest = objectMapper.writeValueAsString(account);
        MvcResult result = mockMvc.perform()

        accountController.transferMoney(transferRequest,principal);
    }

}
