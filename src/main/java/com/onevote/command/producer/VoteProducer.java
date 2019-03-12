package com.onevote.command.producer;

import com.onevote.Constants;
import com.onevote.User;
import com.onevote.Vote;
import com.onevote.command.exception.KafkaProducerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@Service
public class VoteProducer implements Producer<Vote> {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private KafkaTemplate<String, Vote> kafkaTemplate;

    public void sendMessage(Vote vote) {
        logger.info(String.format("#### -> Producing vote -> %s", vote.toString()));
        ListenableFuture<SendResult<String, Vote>> future = kafkaTemplate.send(Constants.VOTE_TOPIC, vote);
        future.addCallback(new ListenableFutureCallback<SendResult<String, Vote>>() {

            @Override
            public void onSuccess(final SendResult<String, Vote> message) {
                logger.info("sent vote= " + vote + "successfully");
                vote.setVoteId(UUID.randomUUID().toString());
            }

            @Override
            public void onFailure(final Throwable throwable) {
                logger.error("unable to send vote= " + vote, throwable);
            }
        });
    }
}
