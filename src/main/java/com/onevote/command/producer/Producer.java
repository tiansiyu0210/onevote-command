package com.onevote.command.producer;

public interface Producer<T> {

    void sendMessage(T t);
}
