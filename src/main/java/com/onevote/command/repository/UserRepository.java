package com.onevote.command.repository;

import com.onevote.User;
import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

    Option<User> findByName(String name);

}