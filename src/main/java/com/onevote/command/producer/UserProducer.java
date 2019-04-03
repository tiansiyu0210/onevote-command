package com.onevote.command.producer;

import com.onevote.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserProducer {

    private static final Logger logger = LoggerFactory.getLogger(UserProducer.class);
    private static final String TOPIC = "users";

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    public void sendMessage(User user) {
        logger.info(String.format("#### -> Producing user -> %s", user.toString()));
        kafkaTemplate.send(TOPIC, user);
    }
}
