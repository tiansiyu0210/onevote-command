package com.onevote.command.config;

import com.onevote.User;
import com.onevote.event.VoteEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public Map<String, Object> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka:9092");
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configProps;
    }


    //User
    @Bean
    public ProducerFactory<String, User> userProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerFactory());
    }

    @Bean
    public KafkaTemplate<String, User> userKafkaTemplate() {
        return new KafkaTemplate<>(userProducerFactory());
    }

    //Vote
    @Bean
    public ProducerFactory<String, VoteEvent> voteEventProducerFactory() {
        return new DefaultKafkaProducerFactory<>(producerFactory());
    }

    @Bean
    public KafkaTemplate<String, VoteEvent> voteEventKafkaTemplate() {
        return new KafkaTemplate<>(voteEventProducerFactory());
    }
}
