package com.bank.api.controllers;

import com.bank.api.dio.EnableNetBankingModel;
import com.bank.api.dio.PersonalDetailsRequest;
import com.bank.api.entity.Account;
import com.bank.api.entity.PersonalDetails;
import com.bank.api.helper.ModelConverter;
import com.bank.api.helper.ValueExtrecterFromPrinciple;
import com.bank.api.services.AccountService;
import com.bank.api.services.PersonalDetailsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Random;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class AccountController {



}
