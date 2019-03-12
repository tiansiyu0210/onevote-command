package com.onevote.command.controller;

import com.onevote.Vote;
import com.onevote.command.exception.KafkaProducerException;
import com.onevote.command.producer.VoteProducer;
import com.onevote.command.repository.VoteRepository;
import com.onevote.command.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/votes")
public class VoteController {

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    VoteProducer voteProducer;

    @RequestMapping(method = RequestMethod.POST)
    public @ResponseBody Vote insertVote(@RequestBody Vote vote) {
        vote.setCreateAt(LocalDateTime.now());
        vote.getOptions().forEach(option -> {
            option.setCreateAt(LocalDateTime.now());
            UserService.setAnonymousUser(option);
        });
        voteProducer.sendMessage(vote);
        if(vote.getVoteId() != null){
            return vote;
        }else{
            throw new KafkaProducerException("unable to send vote= " + vote);
        }

    }
}
