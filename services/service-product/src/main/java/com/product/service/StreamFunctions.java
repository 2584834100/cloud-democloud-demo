package com.product.service;

import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;
import java.util.random.RandomGenerator;
import java.util.stream.IntStream;

@Component
public class StreamFunctions {

    @Bean
    public Consumer<Message<String>> orderConsumer() {
        return msg ->
        {
            long timeMillis = System.currentTimeMillis();
            // 获取消息头中的路由键
            String routingKey = (String) msg.getHeaders().get("amqp_receivedRoutingKey");
            String payload = msg.getPayload();
            System.out.println("收到消息routingKey: " + routingKey + "--" + timeMillis);
            System.out.println("收到消息payload: " + payload + "--" + timeMillis);
        };
    }

    public static void main(String[] args) {
        // 使用新的随机数生成器接口
        RandomGenerator generator = RandomGenerator.getDefault();
        // 生成0-99随机整数
        System.out.println(generator.nextInt(100));
        // 生成10个0-99的随机整数
        IntStream intStream = generator.ints(10, 0, 100);
//        intStream.forEach(x -> System.out.println(x));
        intStream.forEach(System.out::println);
    }
}
