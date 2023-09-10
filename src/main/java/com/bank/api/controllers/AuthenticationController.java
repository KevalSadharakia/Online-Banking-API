package com.bank.api.controllers;


import com.bank.api.auth.JwtHelper;
import com.bank.api.models.JWTRequest;
import com.bank.api.models.JWTResponse;
import com.bank.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/login")
public class AuthenticationController {

    @Autowired
    JwtHelper jwtHelper;

    @Autowired
    UserService userService;
    @PostMapping
    public ResponseEntity<Object> login(@RequestBody JWTRequest jwtRequest){

        if(userService.isValidUser(jwtRequest)){
            String token = jwtHelper.generateToken(jwtRequest.getUsername());
            JWTResponse jwtResponse = new JWTResponse(jwtRequest.getUsername(),token);
            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Invalid User", HttpStatus.BAD_REQUEST);
        }

    }


}
