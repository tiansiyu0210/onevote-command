package com.onevote.command.controller;

import com.onevote.Action;
import com.onevote.User;
import com.onevote.Vote;
import com.onevote.command.producer.VoteProducer;
import com.onevote.command.repository.UserRepository;
import com.onevote.command.repository.VoteSearchRepository;
import com.onevote.command.security.CustomUserDetails;
import com.onevote.command.validation.VoteValidation;
import com.onevote.event.VoteEvent;
import com.onevote.exception.OneVoteRuntimeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController()
public class VoteController {

    private VoteProducer voteProducer;

    private VoteSearchRepository voteSearchRepository;

    private UserRepository userRepository;

    @Autowired
    public VoteController(VoteProducer voteProducer,
                          VoteSearchRepository voteSearchRepository,
                          UserRepository userRepository){
        this.voteProducer = voteProducer;
        this.voteSearchRepository = voteSearchRepository;
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/votes", method = RequestMethod.POST)
    public Vote createVote(@RequestBody Vote vote)  throws OneVoteRuntimeException {

        CustomUserDetails cd = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByName(cd.getUsername()).get();
        currentUser.setPassword("");

        VoteValidation.validateCreateVote(vote);

        vote.setCreateAt(new Date());
        vote.setCreator(currentUser);

        VoteEvent  voteEvent = new VoteEvent();
        voteEvent.setAction(Action.CREATE_VOTE);
        voteEvent.setVote(vote);

        voteProducer.sendVoteEvent(voteEvent);
        return vote;
    }

    @RequestMapping(value = "/votes", method = RequestMethod.GET)
    public List<Vote> getVotes() {

        CustomUserDetails cd = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByName(cd.getUsername()).get();
        currentUser.setPassword("");

        return  voteSearchRepository.findByCreatorId(currentUser);
    }
}
