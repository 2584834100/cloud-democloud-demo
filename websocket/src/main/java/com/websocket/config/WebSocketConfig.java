package com.websocket.config;

import com.websocket.server.WebSocketServer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        // webSocket通道
        // 指定处理器和路径,如：http://www.baidu.com/service-name/websocket?uid=xxxx
        webSocketHandlerRegistry.addHandler(new WebSocketServer(), "/websocket")
//                // 指定自定义拦截器
//                .addInterceptors(new WebSocketInterceptor())
                // 允许跨域
                .setAllowedOrigins("*");
    }
}
