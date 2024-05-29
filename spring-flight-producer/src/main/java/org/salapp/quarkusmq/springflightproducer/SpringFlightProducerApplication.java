package org.salapp.quarkusmq.springflightproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@SpringBootApplication
public class SpringFlightProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringFlightProducerApplication.class, args);
    }

}
