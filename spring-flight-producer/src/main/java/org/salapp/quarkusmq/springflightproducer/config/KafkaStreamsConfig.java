package org.salapp.quarkusmq.springflightproducer.config;

//@Component
//@EnableKafkaStreams
public class KafkaStreamsConfig {


    /*private final WebSocketService webSocketService;

    @Autowired
    public KafkaStreamsConfig(@Qualifier("flightWebSocketService") WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }

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

        stream.foreach((k, v) -> System.out.println(v));

        *//*  stream.filter((key, value) -> value != null && value.getDestination().startsWith("MIA"))
            .foreach((key, value) -> System.out.println("Received message: Key=" + key + " Value=" + value));*//*

        stream.filter((key, value) -> value != null).to("output-topic");
        return stream;
    }

    @Bean
    public KStream<String, Flight> readingFromOutputTopicStream(StreamsBuilder streamsBuilder) {
        KStream<String, Flight> stream = streamsBuilder.stream("output-topic", Consumed.with(Serdes.String(), new JsonSerde<>(Flight.class)));

        stream.foreach((key, flightValue) -> {
            System.out.println("Sending to the WebSocket: Key=" + key + " Value=" + flightValue);
            webSocketService.sendMessage(flightValue);
        });

        return stream;
    }
*/
}
