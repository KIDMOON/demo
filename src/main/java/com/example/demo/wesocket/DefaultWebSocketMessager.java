package com.example.demo.wesocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.standard.StandardWebSocketSession;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultWebSocketMessager implements WebSocketMessager{

    private final Map<String, WebSocketSession> store = new ConcurrentHashMap<>(32);


    @Override
    public void onSessionConnect(WebSocketSession session) {
        store.put(session.getPrincipal().getName(),session);

    }

    @Override
    public void onSessionClose(WebSocketSession session) {
        store.remove(session.getPrincipal().getName());
    }

    @Override
    public void sendAll(TextMessage textMessage) {
        for (WebSocketSession webSocketSession:store.values()){
            try {
                webSocketSession.sendMessage(textMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




    }
}
