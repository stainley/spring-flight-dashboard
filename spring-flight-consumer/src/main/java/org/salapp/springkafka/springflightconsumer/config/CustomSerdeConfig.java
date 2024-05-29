package org.salapp.springkafka.springflightconsumer.config;

import org.salapp.springkafka.springflightconsumer.model.Flight;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
public class CustomSerdeConfig {

    @Bean
    public JsonSerde<Flight> flightJsonSerde() {
        JsonSerde<Flight> flightJsonSerde = new JsonSerde<>(Flight.class);
        flightJsonSerde.deserializer().addTrustedPackages(
            "java.util", "java.lang", "org.salapp.springkafka.springflightconsumer.model", "org.salapp.quarkusmq.springflightproducer.model"
        );
        return flightJsonSerde;
    }
}
