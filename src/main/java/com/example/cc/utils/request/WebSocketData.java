package com.example.cc.utils.request;

import lombok.Data;

import javax.websocket.Session;

@Data
public class WebSocketData {
    private Session session;
    private String communicationId;
}
