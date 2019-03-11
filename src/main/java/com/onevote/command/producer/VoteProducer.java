package com.onevote.command.producer;

import com.onevote.Constants;
import com.onevote.Vote;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class VoteProducer implements Producer<Vote> {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private KafkaTemplate<String, Vote> kafkaTemplate;

    public void sendMessage(Vote vote) {
        logger.info(String.format("#### -> Producing vote -> %s", vote.toString()));
        kafkaTemplate.send(Constants.VOTE_TOPIC, vote);
    }
}
