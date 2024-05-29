package org.salapp.springkafka.springflightconsumer.service;

import org.salapp.springkafka.springflightconsumer.config.WebSocketHandler;
import org.salapp.springkafka.springflightshared.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "flightWebSocketService")
public class WebSocketService implements IWebSocketService {

    private final WebSocketHandler webSocketHandler;

    @Autowired
    public WebSocketService(WebSocketHandler webSocketHandler) {
        this.webSocketHandler = webSocketHandler;
    }

    @Override
    public void sendMessage(Flight flight) {
        webSocketHandler.sendMessage(flight);
    }
}
