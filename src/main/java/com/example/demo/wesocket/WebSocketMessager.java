package com.example.demo.wesocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

public interface WebSocketMessager {

    /**
     * 当session创建时，调用此方法
     *
     * @param session WebSocketSession 实例
     * @throws Exception
     */
    void onSessionConnect(WebSocketSession session);

    /**
     * 当session关闭时，调用此方法
     *
     * @param session WebSocketSession 实例
     * @throws Exception
     */
    void onSessionClose(WebSocketSession session);



    void sendAll(TextMessage textMessage);
}
