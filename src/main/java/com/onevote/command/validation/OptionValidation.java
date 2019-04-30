package com.onevote.command.validation;

import com.onevote.Option;
import com.onevote.Vote;
import com.onevote.exception.OneVoteRuntimeException;
import java.util.Date;
import org.springframework.util.StringUtils;

public class OptionValidation {
  private OptionValidation() {
    throw new AssertionError();
  }

  public static void validateCreateOption(Option option, Vote vote, String userName) throws OneVoteRuntimeException {
    // Check Option name is falsy
    if (StringUtils.isEmpty(option.getName())) {
      throw new OneVoteRuntimeException("option name is empty or null");
    }

    // Check name max length
    if (option.getName().length() >= 200) {
      throw new OneVoteRuntimeException("option name exceeds the maximum of 200 characters");
    }

    // Check option uniqueness
    for (Option op : vote.getOptions()) {
      if (op.getName().equals(option.getName())) {
        throw new OneVoteRuntimeException("this option has been added to the vote");
      }
    }

    // Check Active
    if (!option.getIsActive()) {
      throw new OneVoteRuntimeException("option is no longer active");
    }

    // Check expiration
    if(vote.getExpiration() != null && vote.getExpiration().before(new Date())){
      throw new OneVoteRuntimeException("the vote has expired and cannot be updated");
    }

    // Check if others allowed to create option
    if (!vote.isAllowOthersCreateOption() && !vote.getCreator().getName().equals(userName)) {
      throw new OneVoteRuntimeException("This vote does now allow others to create option except for vote creator");
    }
  }

}
