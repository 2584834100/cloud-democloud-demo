package com.websocket.pojo;

import lombok.Data;

@Data
public class Message {

    private String id;
    private String type;
    private String message;
}
