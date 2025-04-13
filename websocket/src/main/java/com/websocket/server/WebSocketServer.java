package com.websocket.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websocket.pojo.Message;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@ServerEndpoint("/websocket") // 声明为websocket 服务端并通过参数指定访问路径
public class WebSocketServer extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    @OnOpen
    public void afterConnectionEstablished(@NotNull WebSocketSession session)  {
        System.out.println("连接成功");
    }

    @OnMessage
    protected void handleTextMessage(@NotNull WebSocketSession session, @NotNull TextMessage message) throws IOException {
        System.out.println(message.getPayload());
        Message message1 = new ObjectMapper().readValue(message.getPayload(), Message.class);
        userSessions.put(message1.getId(), session);
        session.sendMessage(new TextMessage(message.getPayload()));
    }
    @OnClose
    public void afterConnectionClosed(@NotNull WebSocketSession session, @NotNull CloseStatus status) throws Exception {
        System.out.println("连接关闭");
        super.afterConnectionClosed(session, status);
    }


    // 向特定用户发送消息
    public void sendToUser(String userId, String message) throws IOException {
        WebSocketSession session = userSessions.get(userId);
        if (session != null && session.isOpen()) {
            session.sendMessage(new TextMessage(message));
        }
    }

}
