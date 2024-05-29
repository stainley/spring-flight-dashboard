package org.salapp.springkafka.springflightconsumer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.salapp.springkafka.springflightconsumer.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private final ObjectMapper objectMapper;
    private final ConcurrentMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Autowired
    public WebSocketHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.put(session.getId(), session);
        super.afterConnectionEstablished(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        try (AutoCloseable ac = sessions.remove(session.getId())) {
            ac.close();
            super.afterConnectionClosed(session, status);
        }
    }

    public void sendMessage(Flight message) {
        try {
            String payload = objectMapper.writeValueAsString(message);

            for(WebSocketSession session : sessions.values()) {
                session.sendMessage(new TextMessage(payload));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
