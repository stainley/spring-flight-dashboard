package org.salapp.springkafka.springflightconsumer.service;

import org.salapp.springkafka.springflightconsumer.model.Flight;

public interface IWebSocketService {

    void sendMessage(Flight flight);
}
