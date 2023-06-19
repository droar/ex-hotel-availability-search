package com.droar.hs.command.domain.service;

import com.droar.hs.command.domain.HotelSearch;

public interface HotelSearchDomainEventService {
    void sendEvent(final HotelSearch hotelSearch);
}
