package com.bank.api.helper;

import com.bank.api.models.User;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class ValueExtrecterFromPrinciple {

    public User getUserFromPrinciple(Principal principal){
        String s = new Gson().toJson(principal);
        JsonObject jsonObject = JsonParser.parseString(s).getAsJsonObject();
        JsonElement principle = jsonObject.get("principal");

        User user = new Gson().fromJson(principle,User.class);
        return user;
    }
}
