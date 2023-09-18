package com.bank.api.controllers;


import com.bank.api.auth.JwtHelper;
import com.bank.api.dto.JWTRequest;
import com.bank.api.dto.JWTResponse;
import com.bank.api.entity.PersonalDetails;
import com.bank.api.services.PersonalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin
public class AuthenticationController {

    @Autowired
    JwtHelper jwtHelper;

    @Autowired
    PersonalDetailsService personalDetailsService;
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody JWTRequest jwtRequest){

        if(personalDetailsService.isLoginCredentialValid(jwtRequest)){
            String token = jwtHelper.generateToken(jwtRequest.getUsername());
            JWTResponse jwtResponse = new JWTResponse(jwtRequest.getUsername(),token);
            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
        }else {
            return new ResponseEntity<>("Invalid User", HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/validation")
    public ResponseEntity<Boolean> isValid(Principal principal){
        return new ResponseEntity<>(true,HttpStatus.OK);
    }


}
