package com.github.fabrluc.practicespring.listener;

import com.github.fabrluc.practicespring.configuration.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserEventListener {

    @KafkaListener(id = Constants.LISTENER_ID, topics = Constants.MULTI_PARTITION_TOPIC, groupId = "test-group",
            containerFactory = "kafkaListenerContainerFactory", autoStartup = "false")
    public void processUserEvent(UserEvent userEvent) {
        log.info("Received UserEvent: " + userEvent.getUserEventId());
        UserEventStore.addUserEvent(userEvent);
    }
}
