package com.onevote.command.repository;

import com.onevote.Vote;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface VoteRepository extends MongoRepository<Vote, String> {

    Optional<Vote> findById(String id);

}
