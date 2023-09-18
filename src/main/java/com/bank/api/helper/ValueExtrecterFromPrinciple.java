package com.bank.api.helper;

import com.bank.api.entity.Account;
import com.bank.api.entity.PersonalDetails;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;


public class ValueExtrecterFromPrinciple {

    public static PersonalDetails getDetailsFromPrinciple(Principal principal){
        PersonalDetails currentAccount = (PersonalDetails)(((Authentication)principal).getPrincipal());
        return currentAccount;
    }
}
