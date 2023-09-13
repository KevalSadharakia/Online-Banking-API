package com.bank.api.controllers;


import com.bank.api.auth.JwtHelper;
import com.bank.api.dio.JWTRequest;
import com.bank.api.dio.JWTResponse;
import com.bank.api.services.AccountService;
import com.bank.api.services.PersonalDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Boolean> isValid(){
        return new ResponseEntity<>(true,HttpStatus.OK);
    }


}
