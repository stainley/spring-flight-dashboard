package org.salapp.quarkusmq.springflightproducer.service;

import jakarta.annotation.PostConstruct;
import org.salapp.springkafka.springflightshared.model.Flight;
import org.salapp.springkafka.springflightshared.model.FlightStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;
import java.util.Random;

@Service
public class DashboardProducerService {

    private static final String TOPIC = "flightDashboard-input";
    private final Random random = new Random();

    private final KafkaTemplate<String, Flight> kafkaTemplate;

    private final List<String> flightsType = List.of("Boeing 737", "Airbus A320", "Boeing 787 Dreamliner", "Airbus A350 XWB", "Embraer 190", "Bombardier CRJ-700", "ATR 72", "Boeing 777", "Airbus A380", "Boeing 757");
    private final List<String> origins = List.of("JFK", "MIA", "SQD", "HKG", "LAX", "SIN", "YYZ", "YUL", "YVR");
    private final List<String> destinations = List.of("JFK", "MIA", "SQD", "HKG", "LAX", "SIN", "YYZ", "YUL", "YVR");
    private final List<String> flightsNumbers = List.of("AA1234", "BA5678", "CA9012", "WS9012", "SQ1234", "NZ1234");

    @Autowired
    public DashboardProducerService(@Qualifier("kafkaTemplate") KafkaTemplate<String, Flight> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public String sendMessage(Flight flight, String country) {
        kafkaTemplate.send(TOPIC, country, flight);

        return "Message sent";
    }

    @PostConstruct
    public void sendMessages() {


        Flux.interval(Duration.ofSeconds(30))
                .map(tick -> {

                    String origin = origins.get(random.nextInt(origins.size()));
                    String destination;
                    do {
                        destination = destinations.get(random.nextInt(destinations.size()));
                    } while (origin.equals(destination));

                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime begin = now.plusMinutes(random.nextInt(60));
                    LocalDateTime end = begin.plusHours(random.nextInt(-3, 3)).plusMinutes(random.nextInt(60));

                    // Randomly decide if the flight is on time or delayed
                    FlightStatus status;
                    if (begin.isBefore(now) || end.isBefore(begin)) {
                        status = FlightStatus.DELAYED;
                    } else {
                        status = FlightStatus.ON_TIME;
                    }

                    DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.US);

                    long durationInMinutes = Duration.between(begin, end).toMinutes();

                    return new Flight.Builder()
                            .number(flightsNumbers.get(random.nextInt(flightsNumbers.size())))
                            .type(flightsType.get(random.nextInt(flightsType.size())))
                            .origin(origins.get(random.nextInt(origins.size())))
                            .destination(destination)
                            .begin(formatter.format(begin))
                            .end(formatter.format(end))
                            .delay(String.valueOf(durationInMinutes))
                            .status(status)
                            .build();
                })
                .doOnNext(flight -> kafkaTemplate.send(TOPIC, flight.getDestination(), flight))
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();
    }
}
