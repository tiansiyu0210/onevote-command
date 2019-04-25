package com.onevote.command.producer;

import com.onevote.event.VoteEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class VoteProducer {

    private static final Logger logger = LoggerFactory.getLogger(VoteProducer.class);
    private static final String TOPIC = "vote";

    @Autowired
    private KafkaTemplate<String, VoteEvent> voteEventKafkaTemplate;

    public void sendVoteEvent(VoteEvent voteEvent) {
        logger.info(String.format("#### -> Producing vote event -> %s", voteEvent.toString()));
        voteEventKafkaTemplate.send(TOPIC, voteEvent);
    }
}
