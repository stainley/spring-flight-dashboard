package org.salapp.springkafka.springflightconsumer.kafka;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.salapp.springkafka.springflightconsumer.model.Flight;
import org.salapp.springkafka.springflightconsumer.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

@Component
@EnableKafkaStreams
public class FlightDashboardConsumer {

    private final WebSocketService webSocketService;
    private final JsonSerde<Flight> flightJsonSerde;

    @Autowired
    public FlightDashboardConsumer(@Qualifier("flightWebSocketService") WebSocketService webSocketService, JsonSerde<Flight> flightJsonSerde) {
        this.webSocketService = webSocketService;
        this.flightJsonSerde = flightJsonSerde;
    }


    @Bean
    public KStream<String, Flight> flightConsumeStreams(@Qualifier("defaultKafkaStreamsBuilder") StreamsBuilder builder) {
        KStream<String, Flight> flightResponseStreams = builder.stream("flightDashboard-input",
                Consumed.with(Serdes.String(), flightJsonSerde));

        // Send all info to the WebSocket
        flightResponseStreams.foreach((key, value) -> {
            System.out.println(value);
            webSocketService.sendMessage(value);
        });

        flightResponseStreams.filter((key, flight) -> flight != null && flight.getDestination().equals("SDQ"))
                .foreach((key, value) -> System.out.println("Flights going to DR: " + value));

        flightResponseStreams.filter((key, flight) -> flight != null && flight.getDestination().equals("SDQ"))
                .to("flight-dr-topic");

        return flightResponseStreams;
    }

    @Bean
    public KStream<String, Flight> flightOutputToDRStream(@Qualifier("defaultKafkaStreamsBuilder") StreamsBuilder builder) {
        KStream<String, Flight> flightResponseStreams = builder.stream("flight-dr-topic",
                Consumed.with(Serdes.String(), new JsonSerde<>(Flight.class)));

        flightResponseStreams.foreach((key, value) -> System.out.println(value));

        flightResponseStreams.filter((key, flight) -> flight != null && flight.getDestination().equals("SDQ"))
                .foreach((key, value) -> System.out.println("Flights going to DR: " + value));

        flightResponseStreams.filter((key, flight) -> flight != null && flight.getDestination().equals("SDQ"))
                .to("flight-to-dr");

        return flightResponseStreams;
    }

}
