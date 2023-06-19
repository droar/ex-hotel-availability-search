package com.droar.hs.command.infrastructure.event;

import com.droar.hs.command.domain.HotelSearch;
import com.droar.hs.command.domain.service.HotelSearchDomainEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class HotelSearchEventProducer implements HotelSearchDomainEventService {
    private final static Logger logger = LoggerFactory.getLogger(HotelSearchEventProducer.class);
    private final KafkaTemplate<String, Object> producer;

    @Value(value = "${infrastructure.events.topic-name}")
    private String topicName;

    public HotelSearchEventProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.producer = kafkaTemplate;
    }

    @Override
    public void sendEvent(final HotelSearch hotelSearch) {
        logger.info("Sending event: {}", hotelSearch);

        this.producer.send(this.topicName, hotelSearch);
    }
}
