package com.onevote.command.producer;

import com.onevote.Constants;
import com.onevote.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

import static java.util.UUID.randomUUID;

@Service
public class UserProducer implements Producer<User> {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private KafkaTemplate<String, User> kafkaTemplate;

    @Override
    public void sendMessage(User user) {
        logger.info(String.format("#### -> Producing user -> %s", user.toString()));
        ListenableFuture<SendResult<String, User>> future =  kafkaTemplate.send(Constants.USER_TOPIC, user);
        future.addCallback(new ListenableFutureCallback<SendResult<String, User>>() {

            @Override
            public void onSuccess(final SendResult<String, User> message) {
                logger.info("sent user= " + user + "successfully");
                user.setUserId(UUID.randomUUID().toString());
            }

            @Override
            public void onFailure(final Throwable throwable) {
                logger.error("unable to send user= " + user, throwable);
            }
        });
    }
}
