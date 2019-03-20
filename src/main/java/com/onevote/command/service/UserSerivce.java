package com.onevote.command.service;


import com.onevote.User;
import org.springframework.stereotype.Service;

@Service
public class UserSerivce {

    public boolean validateUser(User user) {
        if (user.getName().length() > 500) {
            new Exception("username tooo long");
        } else {
            return true;
        }

        return false;
    }
}
