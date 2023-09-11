package com.bank.api.controllers;

import com.bank.api.helper.ValueExtrecterFromPrinciple;
import com.bank.api.models.JWTResponse;
import com.bank.api.models.User;
import com.bank.api.models.UserDeleteRequest;
import com.bank.api.services.UserService;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.Gson;
import jakarta.validation.Valid;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    ValueExtrecterFromPrinciple valueExtrecterFromPrinciple;

    @GetMapping("/all")
    ResponseEntity<Object> getUsers(Principal principal){
        User user = valueExtrecterFromPrinciple.getUserFromPrinciple(principal);

        if(user.getAdminAccess()){
            return new ResponseEntity<>(userService.getAll(),HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Not authorized",HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/create")
    ResponseEntity<Object> adduser(@Valid @RequestBody User user){
        try {
            User user1 = userService.addUser(user);
            return new ResponseEntity<Object>(user1, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.IM_USED);
        }
    }

    @DeleteMapping
    ResponseEntity<Object> delete(Principal principal, @RequestBody UserDeleteRequest userDeleteRequest){
        User user = valueExtrecterFromPrinciple.getUserFromPrinciple(principal);

        if(user.getAdminAccess()){
            userService.deleteUser(userDeleteRequest.getUserId());
            return new ResponseEntity<>("done",HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Not authorized",HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/update")
    ResponseEntity<Object> update(Principal principal, @RequestBody User user){
        User user1 = valueExtrecterFromPrinciple.getUserFromPrinciple(principal);
        userService.updateUser(user);

        if(userService.isUserNameExist(user.getUsername())){
            if(user.getAdminAccess()){
                userService.updateUser(user);
                return new ResponseEntity<>("done",HttpStatus.OK);
            }else {
                if(user.getUsername().equals(user1.getUsername())){
                    userService.updateUser(user);
                    return new ResponseEntity<>("done",HttpStatus.OK);
                }else {
                    return new ResponseEntity<>("Unauthorized request",HttpStatus.UNAUTHORIZED);
                }
            }
        }else {
            return new ResponseEntity<>("Username not found",HttpStatus.NOT_FOUND);
        }


    }



}
