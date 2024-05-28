package org.salapp.quarkusmq.springflightproducer.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.salapp.quarkusmq.springflightproducer.model.Flight;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@EnableKafkaStreams
public class KafkaStreamsConfig {

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_BUILDER_BEAN_NAME)
    public StreamsBuilderFactoryBean defaultKafkaStreamsBuilder() {
        Map<String, Object> config = new HashMap<>();
        config.put(org.apache.kafka.streams.StreamsConfig.APPLICATION_ID_CONFIG, "my-kafka-streams-app");
        config.put(org.apache.kafka.streams.StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(org.apache.kafka.streams.StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        config.put(org.apache.kafka.streams.StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class.getName());

        KafkaStreamsConfiguration streamsConfig = new KafkaStreamsConfiguration(config);
        return new StreamsBuilderFactoryBean(streamsConfig);
    }


    @Bean
    public KStream<String, Flight> kStream(StreamsBuilder streamsBuilder) {
        KStream<String, Flight> stream = streamsBuilder.stream("input-topic",
                Consumed.with(Serdes.String(), new JsonSerde<>(Flight.class)));


        stream.filter((key, value) -> value != null && value.getFlightName().startsWith("SDQ"))
            .foreach((key, value) -> System.out.println("Received message: Key=" + key + " Value=" + value));

        stream.filter((key, value) -> value != null && value.getFlightName().startsWith("SDQ")).to("output-topic");
        return stream;
    }

    @Bean
    public KStream<String, String> readingFromOutputTopicStream(StreamsBuilder streamsBuilder) {
        KStream<String, String> stream = streamsBuilder.stream("output-topic");

        stream.foreach((key, value) -> System.out.println("READING FROM OUTPUT: Key=" + key + " Value=" + value));
        /*stream.to("output-topic");*/
        return stream;
    }

}
