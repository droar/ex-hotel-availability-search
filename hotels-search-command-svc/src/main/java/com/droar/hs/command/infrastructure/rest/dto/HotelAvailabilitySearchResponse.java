package com.droar.hs.command.infrastructure.rest.dto;

import java.io.Serializable;

public record HotelAvailabilitySearchResponse(String searchId) implements Serializable {

    @Override
    public String toString() {
        return "HotelAvailabilitySearchResponse{" +
                "searchId='" + searchId + '\'' +
                '}';
    }
}
