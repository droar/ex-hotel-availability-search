package com.droar.hs.command.application.common;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class HotelAvailabilitySearchIdGenerator {

    public String generateId() {
        return UUID.randomUUID().toString();  // Generator could be externalized if needed.
    }
}
