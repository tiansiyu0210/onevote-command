package com.onevote.command.controller;

import com.onevote.User;
import com.onevote.Vote;
import com.onevote.command.exception.RecordNotFoundException;
import com.onevote.command.producer.VoteProducer;
import com.onevote.command.repository.VoteRepository;
import com.onevote.command.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/votes")
public class VoteController {

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    VoteProducer voteProducer;

    @RequestMapping(method = RequestMethod.POST)
    public void insertVote(@RequestBody Vote vote) {
        vote.setCreateAt(LocalDateTime.now());
        vote.getOptions().forEach(option -> {
            option.setCreateAt(LocalDateTime.now());
            UserService.setAnonymousUser(option);
        });
        voteProducer.sendMessage(vote);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Vote> getAllVotes() {
        return voteRepository.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody Vote getVoteById(@PathVariable String id) {
        return voteRepository.findById(id).orElseThrow(() -> new RecordNotFoundException("vote not found for id : " + id));
    }

}
