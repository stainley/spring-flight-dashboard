package org.salapp.springkafka.springflightconsumer.config;

import org.salapp.springkafka.springflightshared.model.Flight;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
public class CustomSerdeConfig {

    @Bean
    public JsonSerde<Flight> flightJsonSerde() {
        JsonSerde<Flight> flightJsonSerde = new JsonSerde<>(Flight.class);
        flightJsonSerde.deserializer().addTrustedPackages(
            "java.util", "java.lang"
        );
        return flightJsonSerde;
    }
}
