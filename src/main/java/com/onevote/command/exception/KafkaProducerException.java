package com.onevote.command.exception;

public class KafkaProducerException extends RuntimeException {
    public KafkaProducerException(String exception) {
        super(exception);
    }
}
