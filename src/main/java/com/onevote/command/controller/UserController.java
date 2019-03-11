package com.onevote.command.controller;

import com.onevote.User;
import com.onevote.command.repository.UserRepository;
import com.onevote.command.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Producer userProducer;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public void insertUser(@RequestBody User user) {
        userProducer.sendMessage(user);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

}