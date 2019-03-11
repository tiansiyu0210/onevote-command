package com.onevote.command.service;


import com.onevote.BaseModel;
import com.onevote.User;

public class UserService {

    private static User anonymousUser = new User();

    //since we don't persist any user for current version
    //set an anonymous user for now.
    public static void setAnonymousUser(BaseModel baseModel){
        baseModel.setCreator(anonymousUser);
        baseModel.setCreator(anonymousUser);
    }
}
