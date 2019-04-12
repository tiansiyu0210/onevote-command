package com.onevote.command.service;

import com.onevote.User;
import com.onevote.command.repository.UserRepository;
import com.onevote.exception.OneVoteRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class UserService {


    @Autowired
    UserRepository userRepository;

    public AtomicBoolean isUserExist(User user) throws OneVoteRuntimeException{
        AtomicBoolean isExist = new AtomicBoolean(false);
        userRepository.findByName(user.getName()).ifPresent((u)-> {
            isExist.set(true);
        });

        return isExist;
    }
}
