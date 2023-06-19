package com.droar.hs.command.application.mapper;


import com.droar.hs.command.domain.HotelSearch;
import com.droar.hs.command.infrastructure.rest.dto.HotelAvailabilitySearchRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HotelSearchRequestMapper {

    public Optional<HotelSearch> toDomainEntity(final String searchId, final HotelAvailabilitySearchRequest request) {
        return Optional.ofNullable(request)
                .filter(it -> searchId != null)
                .map(it -> new HotelSearch(
                        searchId,
                        request.hotelId(),
                        request.checkIn(),
                        request.checkOut(),
                        request.ages()
                ));
    }
}
