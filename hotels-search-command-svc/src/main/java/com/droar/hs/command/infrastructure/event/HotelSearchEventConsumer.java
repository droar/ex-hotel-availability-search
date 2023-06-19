package com.droar.hs.command.infrastructure.event;

import com.droar.hs.command.domain.HotelSearch;
import com.droar.hs.command.domain.repository.HotelSearchDomainRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class HotelSearchEventConsumer {
    private final static Logger logger = LoggerFactory.getLogger(HotelSearchEventConsumer.class);

    private final HotelSearchDomainRepository repository;

    public HotelSearchEventConsumer(final HotelSearchDomainRepository hotelSearchDomainRepository) {
        this.repository = hotelSearchDomainRepository;
    }

    @KafkaListener(topics = "${infrastructure.events.topic-name}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(@Payload final HotelSearch hotelSearchMessage) {
        logger.info("Consuming message: {}", hotelSearchMessage);

        try {
            this.repository.save(hotelSearchMessage);
        } catch (RuntimeException e) {
            logger.error("An error occurred trying to save the consumed message {}", hotelSearchMessage);
        }
    }
}
