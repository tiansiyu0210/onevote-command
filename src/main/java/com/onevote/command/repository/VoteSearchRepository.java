package com.onevote.command.repository;

import com.onevote.User;
import com.onevote.Vote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
public class VoteSearchRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Vote> findByCreatorId(User user){
        //need pagination
         List<Vote> votes =mongoTemplate.find(query(where("creator.id").is(user.getId())), Vote.class);
         return votes;
    }
}
