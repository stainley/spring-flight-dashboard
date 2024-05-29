package org.salapp.quarkusmq.springflightproducer.config;

import org.springframework.web.socket.handler.TextWebSocketHandler;

//@Component
public class WebSocketHandler extends TextWebSocketHandler {

    /*private final ObjectMapper mapper;
    private final ConcurrentMap<String, WebSocketSession> sessions = new ConcurrentHashMap<>();


    @Autowired
    public WebSocketHandler(ObjectMapper mapper) {
        this.mapper = mapper;
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
        try(AutoCloseable ac = sessions.remove(session.getId())) {
            super.afterConnectionClosed(session, status);
        }
    }

    public void sendMessage(Flight flight) {
        try {
            String payload = mapper.writeValueAsString(flight);

            for(WebSocketSession session: sessions.values()) {
                session.sendMessage(new TextMessage(payload));
            }

        }catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
