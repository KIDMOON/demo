package com.example.demo.wesocket;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.List;

public class CommandWebSocketMessageDispatcher extends TextWebSocketHandler {


    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<WebSocketMessager> webSocketMessagers;

    public List<WebSocketMessager> getWebSocketMessagers() {
        return webSocketMessagers;
    }

    public void setWebSocketMessagers(List<WebSocketMessager> webSocketMessagers) {
        this.webSocketMessagers = webSocketMessagers;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        if (StringUtils.isEmpty(payload)) {
            return;
        }

        Message message1=new Message();
        message1.setContext(message.getPayload());
        message1.setTime(DateUtil.now());
        message1.setSender(session.getPrincipal());
        message=new TextMessage(JSON.toJSONString(message1));
        if (webSocketMessagers != null) {
            for (WebSocketMessager webSocketMessager:webSocketMessagers){
                webSocketMessager.sendAll(message);
            }
        }
    }


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.debug("new WebSocket Session Established,sessionId:{}",session.getId());

        if (webSocketMessagers != null) {
           for (WebSocketMessager webSocketMessager:webSocketMessagers){
               webSocketMessager.onSessionConnect(session);

           }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.debug("WebSocket Session Closed,sessionId:{}",session.getId());

        if (webSocketMessagers != null) {
            for (WebSocketMessager webSocketMessager:webSocketMessagers){
                webSocketMessager.onSessionClose(session);

            }
        }
    }
}
