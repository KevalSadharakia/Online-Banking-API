package com.bank.api.helper;

import com.bank.api.entity.Account;
import com.bank.api.entity.PersonalDetails;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.security.Principal;


public class ValueExtrecterFromPrinciple {

    public static PersonalDetails getDetailsFromPrinciple(Principal principal){
        String s = new Gson().toJson(principal);
        JsonObject jsonObject = JsonParser.parseString(s).getAsJsonObject();
        JsonElement principle = jsonObject.get("principal");

        PersonalDetails personalDetails = new Gson().fromJson(principle,PersonalDetails.class);
        return personalDetails;
    }
}
