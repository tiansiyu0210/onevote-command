package com.onevote.command.validation;

import com.onevote.Vote;
import com.onevote.exception.OneVoteRuntimeException;
import org.springframework.util.StringUtils;

import java.util.Date;

public class VoteValidation {

    private VoteValidation(){
        throw new AssertionError();
    }

    public static void validateCreateVote(Vote vote) throws OneVoteRuntimeException {
        if(StringUtils.isEmpty(vote.getTitle())){
            throw new OneVoteRuntimeException("vote must have a title");
        }

        if(vote.getExpiration() != null && vote.getExpiration().after(new Date())){
            throw new OneVoteRuntimeException("expiration must be a future time");
        }
    }
}
