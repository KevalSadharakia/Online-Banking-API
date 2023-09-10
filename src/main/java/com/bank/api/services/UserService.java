package com.bank.api.services;

import com.bank.api.models.JWTRequest;
import com.bank.api.models.User;
import com.bank.api.repositories.UserRepositories;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepositories userRepositories;


    public boolean isUserNameExist(String username){
        return userRepositories.existsById(username);
    }
    public User addUser(@Valid User user) {
        if(isUserNameExist(user.getUsername())){
            throw new RuntimeException("Username is already in use.");
        }
        return userRepositories.save(user);
    }

    public boolean isValidUser(JWTRequest jwtRequest){
        User user = userRepositories.findById(jwtRequest.getUsername()).get();
        if(user==null){
            return false;
        }else {
            return user.getPassword().equals(jwtRequest.getPassword());
        }
    }

    public User getUser(String username) {
        User user = userRepositories.findById(username).get();
        return user;
    }


    public void deleteUser(String userId){
        userRepositories.deleteById(userId);
    }

    public void updateUser(User user){
        try {
            userRepositories.save(user);
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }



    public List<User> getAll() {
        return userRepositories.findAll();
    }




}
