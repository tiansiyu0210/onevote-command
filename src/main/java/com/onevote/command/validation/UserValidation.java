package com.onevote.command.validation;

import com.onevote.User;
import com.onevote.exception.OneVoteRuntimeException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class UserValidation {

    private UserValidation(){
        throw new AssertionError();
    }

    public static void validUserCreation(User user) throws OneVoteRuntimeException {
        if(StringUtils.isEmpty(user.getName())){
            throw new OneVoteRuntimeException("username is empty");
        }

        if(StringUtils.isEmpty(user.getPassword())){
            throw new OneVoteRuntimeException("password is empty");
        }

        if(CollectionUtils.isEmpty(user.getRoles())){
            throw new OneVoteRuntimeException("role is empty");
        }
    }
}
