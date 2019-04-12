package com.onevote.command.validation;

import com.onevote.Vote;
import com.onevote.exception.OneVoteRuntimeException;
import org.springframework.util.StringUtils;

import java.util.Date;

public class VoteValidation {

    private VoteValidation(){ throw new AssertionError(); }

    public static void validateCreateVote(Vote vote) throws OneVoteRuntimeException {
        if(vote == null){
            throw new OneVoteRuntimeException("vote is empty");
        }

        if(StringUtils.isEmpty(vote.getTitle())){
            throw new OneVoteRuntimeException("vote must have a title");
        }

        if(vote.getExpiration() != null && vote.getExpiration().before(new Date())){
            throw new OneVoteRuntimeException("expiration must be a future time");
        }
    }

    public static void validateUpdateVote(Vote newVote) throws OneVoteRuntimeException {
        if(newVote.getNote() == null &&
           newVote.getTitle() == null &&
           newVote.getExpiration() == null){
            throw new OneVoteRuntimeException("invalid update body");
        }

        if (newVote.getExpiration() != null &&
            newVote.getExpiration().before(new Date())){
            throw new OneVoteRuntimeException("expiration must be a future time");
        }

    }

}
