package com.onevote.command.controller;

import com.onevote.Option;
import com.onevote.User;
import com.onevote.Vote;
import com.onevote.command.producer.VoteProducer;
import com.onevote.command.repository.UserRepository;
import com.onevote.command.repository.VoteRepository;
import com.onevote.command.security.CustomUserDetails;
import com.onevote.command.service.OptionService;
import com.onevote.command.validation.OptionValidation;
import com.onevote.exception.OneVoteRuntimeException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class OptionController {

  private static final Logger logger = LoggerFactory.getLogger(OptionController.class);

  private VoteProducer voteProducer;

  private VoteRepository voteRepository;

  private UserRepository userRepository;

  private OptionService optionService;

  @Autowired
  public OptionController(VoteProducer voteProducer, VoteRepository voteRepository, UserRepository userRepository, OptionService optionService) {
    this.voteProducer = voteProducer;
    this.voteRepository = voteRepository;
    this.userRepository = userRepository;
    this.optionService = optionService;
  }

  @RequestMapping(value = "/vote/options", method = RequestMethod.POST)
  public Option createOption(@RequestBody Option option) throws OneVoteRuntimeException {
    String voteId = option.getVoteId();
    Optional<Vote> optionalVote = voteRepository.findById(voteId);
    optionalVote.orElseThrow(() -> new OneVoteRuntimeException("no vote found for this id: " + voteId));

    Vote vote = optionalVote.get();

    CustomUserDetails cd = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    User currentUser = userRepository.findByName(cd.getUsername()).get();
    currentUser.setPassword("");

    OptionValidation.validateCreateOption(option, vote, currentUser.getName());

    return optionService.createVoteOption(vote, option, currentUser);
  }
}
