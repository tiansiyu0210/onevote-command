package com.onevote.command.service;

import com.onevote.Action;
import com.onevote.User;
import com.onevote.Vote;
import com.onevote.command.producer.VoteProducer;
import com.onevote.event.VoteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.UUID;


@Service
public class VoteService {

    @Autowired
    private VoteProducer voteProducer;

    public Vote updateVote(Vote oldVote, Vote newVote){
        if(newVote.getTitle() != null){
            oldVote.setTitle(newVote.getTitle());
        }

        if(newVote.getNote() != null){
            oldVote.setNote(newVote.getNote());
        }

        if(newVote.getExpiration() != null){
            oldVote.setExpiration(newVote.getExpiration());
        }

        VoteEvent voteEvent = new VoteEvent();
        voteEvent.setAction(Action.UPDATE_VOTE);
        voteEvent.setVote(oldVote);
        voteProducer.sendVoteEvent(voteEvent);

        return oldVote;
    }

    public void completeVoteCreation(Vote vote, User currentUser){
        vote.setCreateAt(new Date());
        vote.setCreator(currentUser);
        if(!CollectionUtils.isEmpty(vote.getOptions())){
            vote.getOptions().forEach(option -> {
                option.setId(UUID.randomUUID().toString());
                option.setCreateAt(new Date());
                option.setCreator(currentUser);
            });
        }
    }

}
