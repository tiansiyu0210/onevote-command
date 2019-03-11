package com.onevote.command.producer;

import com.onevote.Constants;
import com.onevote.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserProducer implements Producer<User> {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    @Override
    public void sendMessage(User user) {
        logger.info(String.format("#### -> Producing user -> %s", user.toString()));
        kafkaTemplate.send(Constants.USER_TOPIC, user);
    }
}
