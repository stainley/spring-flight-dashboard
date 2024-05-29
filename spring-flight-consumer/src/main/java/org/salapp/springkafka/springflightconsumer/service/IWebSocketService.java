package org.salapp.springkafka.springflightconsumer.service;


import org.salapp.springkafka.springflightshared.model.Flight;

public interface IWebSocketService {

    void sendMessage(Flight flight);
}
