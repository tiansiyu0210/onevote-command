package com.onevote.command.util;

import com.onevote.User;
import com.onevote.command.security.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

public class UserUtil {

    private static final Logger logger = LoggerFactory.getLogger(UserUtil.class);

    public static User getCurrentUser(Principal principal){
        User user = new User();
        user.builder().name(principal.getName());
        return user;
    }


    public static User getCurrentUser(CustomUserDetails customUserDetails){
        logger.info("Authenticate:SecurityContextHolder.getContext().getAuthentication()="+ SecurityContextHolder.getContext().getAuthentication());
        logger.info("AuthenticateSecurityContextHolder.getContext().getAuthentication().getPrincipal()="+SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        logger.info("AuthenticateSecurityContextHolder.getContext().getAuthentication().getPrincipal()="+SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        logger.info("Authenticate=SecurityContextHolder.getContext().getAuthentication().getCredentials()="+SecurityContextHolder.getContext().getAuthentication().getCredentials());
        logger.info("Authenticate=SecurityContextHolder.getContext().getAuthentication().getAuthorities()="+SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        logger.info("Authenticate=SecurityContextHolder.getContext().getAuthentication().getDetails()="+SecurityContextHolder.getContext().getAuthentication().getDetails());
        User user = new User();
        user.builder().id(customUserDetails.getId()).name(customUserDetails.getName());
        return user;
    }
}
