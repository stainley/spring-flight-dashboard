package org.salapp.springkafka.springflightconsumer.config;

import org.apache.kafka.common.serialization.Serdes;
import org.salapp.springkafka.springflightconsumer.model.Flight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaStreamsConfig {

    private final JsonSerde<Flight> flightJsonSerde;

    @Autowired
    public KafkaStreamsConfig(JsonSerde<Flight> flightJsonSerde) {
        this.flightJsonSerde = flightJsonSerde;
    }

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public StreamsBuilderFactoryBean streamsBuilderFactoryBean(){
        Map<String, Object> config = new HashMap<>();

        config.put(org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG, "flight-info-streams-consumer-app");
        config.put(org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(org.apache.kafka.streams.StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        config.put(org.apache.kafka.streams.StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, flightJsonSerde.getClass().getName());

        KafkaStreamsConfiguration kafkaStreamsConfiguration = new KafkaStreamsConfiguration(config);
        return new StreamsBuilderFactoryBean(kafkaStreamsConfiguration);
    }
}
