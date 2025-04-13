package com.order.service;

import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class StreamFunctions {

//    @Bean
//    public Supplier<Message<String>> orderProducer() {
//        String message = "Hello, Spring Cloud Stream!";
//        System.out.println("发送消息：" + message);
//        return () -> MessageBuilder.withPayload(message).build();
//    }
}
