package org.salapp.quarkusmq.springflightproducer.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.salapp.quarkusmq.springflightproducer.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
public class DashboardProducerService {

    private static final String TOPIC = "input-topic";

    private final KafkaTemplate<String, Flight> kafkaTemplate;

    @Autowired
    public DashboardProducerService(@Qualifier("kafkaTemplate") KafkaTemplate<String, Flight> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String sendMessage(Flight flight, String country) {
        kafkaTemplate.send(TOPIC, country, flight);
        return "Message sent";
    }

    @PostConstruct
    public void sendMessages(){

        Flux.interval(Duration.ofSeconds(10))
                .map(tick -> new Flight.Builder()
                        .flightDate("SDQ")
                        .flightNo(String.valueOf(new Random().nextInt(10)))
                        .flightDate(LocalDateTime.now().toString())
                        .build())
                .doOnNext(flight -> kafkaTemplate.send(TOPIC, "Message", flight))
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();
    }
}
