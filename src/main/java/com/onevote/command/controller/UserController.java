package com.onevote.command.controller;

import com.onevote.User;
import com.onevote.command.exception.KafkaProducerException;
import com.onevote.command.repository.UserRepository;
import com.onevote.command.producer.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Producer userProducer;

    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public @ResponseBody
    User insertUser(@RequestBody User user) {
        userProducer.sendMessage(user);
        if(user.getUserId() != null){
            return user;
        }else{
            throw new KafkaProducerException("unable to send user= " + user);
        }

    }

}