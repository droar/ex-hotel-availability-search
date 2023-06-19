package com.droar.hs.command.infrastructure.repository.mapper;


import com.droar.hs.command.domain.HotelSearch;
import com.droar.hs.command.infrastructure.repository.entities.HotelSearchJpaEntity;
import org.springframework.stereotype.Component;

import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Component
public class HotelSearchJpaMapper {

    public Optional<HotelSearchJpaEntity> toJpaEntity(final HotelSearch hotelSearch) {
        return Optional.ofNullable(hotelSearch)
                .filter(it -> it.searchId() != null)
                .map(it -> new HotelSearchJpaEntity(
                        hotelSearch.searchId(),
                        hotelSearch.hotelId(),
                        Date.from(hotelSearch.checkIn().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        Date.from(hotelSearch.checkOut().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        hotelSearch.ages()
                ));
    }
}
