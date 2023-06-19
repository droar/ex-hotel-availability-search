package com.droar.hs.query.service.mapper;


import com.droar.hs.query.controller.dto.HotelAvailabilityRetrieveResponse;
import com.droar.hs.query.controller.dto.RetrieveSearchResponse;
import com.droar.hs.query.dao.entities.HotelSearchJpaEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class HotelSearchJpaMapper {

    public HotelAvailabilityRetrieveResponse toDto(final HotelSearchJpaEntity entity, final Integer similarCount) {
        return Optional.ofNullable(entity)
                .map(it -> new HotelAvailabilityRetrieveResponse(
                        entity.getSearchId(),
                        new RetrieveSearchResponse(
                                entity.getHotelId(),
                                entity.getCheckIn(),
                                entity.getCheckOut(),
                                entity.getAges()
                        ),
                        similarCount
                )).orElse(null);
    }
}
