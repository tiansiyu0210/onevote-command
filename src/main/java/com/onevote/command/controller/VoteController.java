package com.onevote.command.controller;

import com.onevote.Action;
import com.onevote.User;
import com.onevote.Vote;
import com.onevote.command.producer.VoteProducer;
import com.onevote.command.repository.UserRepository;
import com.onevote.command.repository.VoteRepository;
import com.onevote.command.repository.VoteSearchRepository;
import com.onevote.command.security.CustomUserDetails;
import com.onevote.command.service.VoteService;
import com.onevote.command.validation.VoteValidation;
import com.onevote.event.VoteEvent;
import com.onevote.exception.OneVoteRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController()
public class VoteController {

    private static final Logger logger = LoggerFactory.getLogger(VoteController.class);

    private VoteProducer voteProducer;

    private VoteSearchRepository voteSearchRepository;

    private UserRepository userRepository;

    private VoteRepository voteRepository;

    private VoteService voteService;

    @Autowired
    public VoteController(VoteProducer voteProducer,
                          VoteSearchRepository voteSearchRepository,
                          UserRepository userRepository,
                          VoteRepository voteRepository,
                          VoteService voteService){
        this.voteProducer = voteProducer;
        this.voteSearchRepository = voteSearchRepository;
        this.userRepository = userRepository;
        this.voteRepository = voteRepository;
        this.voteService = voteService;
    }

    @RequestMapping(value = "/votes", method = RequestMethod.POST)
    public Vote createVote(@RequestBody Vote vote) throws OneVoteRuntimeException {

        CustomUserDetails cd = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByName(cd.getUsername()).get();
        currentUser.setPassword("");

        VoteValidation.validateCreateVote(vote);

        voteService.completeVoteCreation(vote, currentUser);

        VoteEvent voteEvent = new VoteEvent();
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

    @RequestMapping(value = "/votes/{id}", method = RequestMethod.POST)
    public Vote updateVote(@RequestBody Vote updatedVote, @PathVariable("id") String id)  throws OneVoteRuntimeException {
        Optional<Vote> voteOption = voteRepository.findById(id);
        voteOption.orElseThrow(() -> new OneVoteRuntimeException("no vote found for this id: " + id));
        Vote oldVote = voteOption.get();

        VoteValidation.validateUpdateVote(updatedVote);

        CustomUserDetails cd = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = userRepository.findByName(cd.getUsername()).get();
        currentUser.setPassword("");

        //only creator can update vote
        if(!oldVote.getCreator().getId().equals(currentUser.getId())){
            throw new OneVoteRuntimeException("only the creator can update vote");
        }

        oldVote.setModifiedAt(new Date());
        oldVote.setModifier(currentUser);

        return voteService.updateVote(oldVote, updatedVote);
    }

}
