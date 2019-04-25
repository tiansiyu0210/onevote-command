package com.onevote.command.service;

import com.onevote.Action;
import com.onevote.Option;
import com.onevote.User;
import com.onevote.Vote;
import com.onevote.command.producer.VoteProducer;
import com.onevote.event.VoteEvent;
import java.util.Date;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OptionService {

  @Autowired
  private VoteProducer voteProducer;

  public Option createVoteOption(Vote vote, Option option, User currentUser) {
    option.setId(UUID.randomUUID().toString());
    option.setCreateAt(new Date());
    option.setCreator(currentUser);
    vote.getOptions().add(option);

    VoteEvent voteEvent = new VoteEvent();
    voteEvent.setAction(Action.CREATE_OPTION);
    voteEvent.setVote(vote);
    voteProducer.sendVoteEvent(voteEvent);
    return option;
  }
}
