package com.onevote.command.service;

import com.onevote.User;
import com.onevote.command.repository.UserRepository;
import com.onevote.command.security.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUsers = userRepository.findByName(username);

        optionalUsers
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        CustomUserDetails userDetails = optionalUsers.map(CustomUserDetails::new).get();
        return userDetails;
    }
}
