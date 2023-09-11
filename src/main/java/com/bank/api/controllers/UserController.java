package com.bank.api.controllers;

import com.bank.api.helper.ValueExtrecterFromPrinciple;
import com.bank.api.models.*;
import com.bank.api.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.filter.OncePerRequestFilter;

import java.security.Principal;
import java.util.Random;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(OncePerRequestFilter.class);

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
    ResponseEntity<Object> adduser(@Valid @RequestBody UserRequest userRequest){
        try {
            User user = new User();
            user.setFirstName(userRequest.getFirstName());
            user.setLastName(userRequest.getLastName());
            user.setUsername(userRequest.getUsername());
            user.setPassword(userRequest.getPassword());
            user.setIdentityProofNumber(userRequest.getIdentityProofNumber());
            user.setContactNumber(userRequest.getContactNumber());
            user.setAdminAccess(false);
            user.setAddress(userRequest.getAddress());
            user.setGender(userRequest.getGender());
            user.setDob(userRequest.getDob());
            user.setFatherName(userRequest.getFatherName());
            user.setMotherName(userRequest.getMotherName());
            user.setAddress(userRequest.getAddress());
            user.setEmail(userRequest.getEmail());
            int accountNumber = new Random().nextInt(0,Integer.MAX_VALUE);
            user.setAccountNumber(accountNumber);


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

    @PostMapping("/updateAdmin")
    ResponseEntity<Object> update(Principal principal, @RequestBody AdminUpdateRequest adminUpdateRequest) {
        User user= valueExtrecterFromPrinciple.getUserFromPrinciple(principal);
        if(user.getAdminAccess()){
            User targetUser = userService.getUser(adminUpdateRequest.getUsername());
            if(targetUser!=null){
                targetUser.setAdminAccess(adminUpdateRequest.isAdmin());
                userService.updateUser(targetUser);
                return new ResponseEntity<>(targetUser,HttpStatus.OK);
            }else{
                return new ResponseEntity<>("target username not found",HttpStatus.NOT_FOUND);
            }

        }else{
            return new ResponseEntity<>("unauthorized",HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/update")
    ResponseEntity<Object> update(Principal principal, @RequestBody UserRequest userRequest){
        logger.info("update request");
        User user1 = valueExtrecterFromPrinciple.getUserFromPrinciple(principal);
      //  userService.updateUser(user);

        User oldUser = userService.getUser(userRequest.getUsername());

        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUsername(userRequest.getUsername());
        user.setPassword(userRequest.getPassword());
        user.setIdentityProofNumber(userRequest.getIdentityProofNumber());
        user.setContactNumber(userRequest.getContactNumber());
        user.setAdminAccess(oldUser.getAdminAccess());
        user.setFatherName(userRequest.getFatherName());
        user.setMotherName(userRequest.getMotherName());
        user.setAddress(userRequest.getAddress());
        user.setGender(userRequest.getGender());
        user.setDob(userRequest.getDob());
        user.setEmail(userRequest.getEmail());
        user.setAccountNumber(oldUser.getAccountNumber());

        if(userService.isUserNameExist(user.getUsername())){
            logger.info("user exist");
            if(user1.getAdminAccess()){
                logger.info("user has admin access");
                userService.updateUser(user);
                return new ResponseEntity<>("done",HttpStatus.OK);
            }else {
                logger.info("user has no admin access");
                if(user.getUsername().equals(user1.getUsername())){
                    logger.info("user is changing his own details");
                    userService.updateUser(user);
                    return new ResponseEntity<>(user,HttpStatus.OK);
                }else {
                    return new ResponseEntity<>("Unauthorized request",HttpStatus.UNAUTHORIZED);
                }
            }
        }else {
            return new ResponseEntity<>("Username not found",HttpStatus.NOT_FOUND);
        }


    }



}
