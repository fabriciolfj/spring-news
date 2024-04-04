package com.github.fabrluc.practicespring;

import com.github.fabrluc.practicespring.configuration.Constants;
import com.github.fabrluc.practicespring.listener.KafkaListenerControlService;
import com.github.fabrluc.practicespring.listener.UserEvent;
import com.github.fabrluc.practicespring.listener.UserEventStore;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.LongSerializer;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofSeconds;
import static org.awaitility.Awaitility.await;
import static org.junit.Assert.assertEquals;

@Testcontainers
@SpringBootTest
public class KafkaEnableDisableListenerTest {

    private static final Logger logger = LoggerFactory.getLogger(KafkaEnableDisableListenerTest.class);

    @Container
    private static KafkaContainer KAFKA_COTAINER = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"));

    @Autowired
    private KafkaListenerControlService kafkaListenerControlService;

    private static KafkaProducer<Long, UserEvent> producer;

    @DynamicPropertySource
    static void setProps(final DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", KAFKA_COTAINER::getBootstrapServers);
    }

    @BeforeAll
    static void beforeAll() {
        Properties producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_COTAINER.getBootstrapServers());
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        producer = new KafkaProducer<>(producerProperties);
        Awaitility.setDefaultTimeout(ofSeconds(5));
        Awaitility.setDefaultPollInterval(ofMillis(50));
    }

    @Test
    void execute() throws ExecutionException, InterruptedException {
        kafkaListenerControlService.startListener(Constants.LISTENER_ID);

        UserEvent startUserEventTest = new UserEvent(UUID.randomUUID().toString());
        producer.send(new ProducerRecord<>(Constants.MULTI_PARTITION_TOPIC, startUserEventTest));
        await().untilAsserted(() -> assertEquals(1,UserEventStore.getUserEvents().size()));
        UserEventStore.clearUserEvents();

        for (long count = 1; count <= 10; count++) {
            UserEvent userEvent = new UserEvent(UUID.randomUUID().toString());
            Future<RecordMetadata> future = producer.send(new ProducerRecord<>(Constants.MULTI_PARTITION_TOPIC, userEvent));
            RecordMetadata metadata = future.get();
            if (count == 4) {
                await().untilAsserted(() -> assertEquals(4, UserEventStore.getUserEvents().size()));
                this.kafkaListenerControlService.stopListener(Constants.LISTENER_ID);
                UserEventStore.clearUserEvents();
            }
            logger.info("User Event ID: " + userEvent.getUserEventId() + ", Partition : " + metadata.partition());
        }
        assertEquals(0, UserEventStore.getUserEvents().size());
        kafkaListenerControlService.startListener(Constants.LISTENER_ID);
        await().untilAsserted(() -> Assertions.assertEquals(6, UserEventStore.getUserEvents().size()));
        kafkaListenerControlService.stopListener(Constants.LISTENER_ID);
    }

    @AfterAll
    static void destroy() {
        KAFKA_COTAINER.stop();
    }
}
