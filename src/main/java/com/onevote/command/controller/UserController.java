package com.onevote.command.controller;

import com.onevote.User;
import com.onevote.command.producer.UserProducer;
import com.onevote.command.repository.UserRepository;
import com.onevote.command.security.CustomUserDetails;
import com.onevote.command.service.UserService;
import com.onevote.command.validation.UserValidation;
import com.onevote.exception.OneVoteRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController()
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserProducer userProducer;

    @Autowired
    UserService userService;


    @PostMapping("/users")
    public void insertUser(@RequestBody User user) throws OneVoteRuntimeException {
        UserValidation.validUserCreation(user);
        if(userService.isUserExist(user).get()){
            throw new OneVoteRuntimeException("username token");
        }

        user.setCreateAt(new Date());

        userProducer.sendMessage(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public List<User> getAllUser() {
        return userRepository.findAll();
    }


    @GetMapping("/users/me")
    public User getMe() {
        CustomUserDetails cd = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByName(cd.getUsername()).get();
        currentUser.setPassword("");
        return currentUser;
    }
}