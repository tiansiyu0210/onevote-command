package com.onevote.command.controller;

import com.onevote.User;
import com.onevote.command.service.Producer;
import com.onevote.command.repository.UserRepository;
import com.onevote.command.service.UserSerivce;
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
    Producer producer;

    @Autowired
    UserSerivce userSerivce;


    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public void insertUser(@RequestBody com.onevote.User user) {
       // producer.sendMessage(user);
        userRepository.save(user);
        //userRepository.save(user);
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getAllUser() {

        System.out.println("!!!!!!!!######8085");
        return userRepository.findAll();
    }

    @RequestMapping(value = "/aaa", method = RequestMethod.GET)
    public List<User> test1() {
        return userRepository.findAll();
    }

    @RequestMapping(value = "/bbb", method = RequestMethod.GET)
    public List<User> test2() {
        return userRepository.findAll();
    }


}