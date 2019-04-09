package com.onevote.command.service;

import com.onevote.User;
import com.onevote.command.security.CustomUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public User getCurrentUser(User user1){
        User user = new User();
        if (SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {

            user.builder().id(user1.getId()).name(user1.getName());
            return user;
        }
        return user;
    }
}
