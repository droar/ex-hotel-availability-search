package com.droar.hs.command.application.services;

import com.droar.hs.command.application.common.ApplicationException;
import com.droar.hs.command.application.common.HotelAvailabilitySearchIdGenerator;
import com.droar.hs.command.application.mapper.HotelSearchRequestMapper;
import com.droar.hs.command.application.usecases.HotelAvailabilitySearchUseCase;
import com.droar.hs.command.domain.service.HotelSearchDomainEventService;
import com.droar.hs.command.infrastructure.rest.dto.HotelAvailabilitySearchRequest;
import com.droar.hs.command.infrastructure.rest.dto.HotelAvailabilitySearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class HotelAvailabilitySearchService implements HotelAvailabilitySearchUseCase {

    private final static Logger logger = LoggerFactory.getLogger(HotelAvailabilitySearchService.class);
    private final HotelSearchDomainEventService eventService;
    private final HotelSearchRequestMapper mapper;
    private final HotelAvailabilitySearchIdGenerator idGenerator;

    public HotelAvailabilitySearchService(final HotelSearchDomainEventService hotelSearchDomainEventService,
                                          final HotelSearchRequestMapper hotelSearchRequestMapper,
                                          final HotelAvailabilitySearchIdGenerator hotelAvailabilitySearchIdGenerator) {
        this.eventService = hotelSearchDomainEventService;
        this.mapper = hotelSearchRequestMapper;
        this.idGenerator = hotelAvailabilitySearchIdGenerator;
    }

    @Override
    public HotelAvailabilitySearchResponse search(final HotelAvailabilitySearchRequest request) throws ApplicationException {
        final String generatedSearchId = this.idGenerator.generateId();
        logger.info("Issuing a search request for generated id {}", generatedSearchId);

        try {
            this.mapper.toDomainEntity(generatedSearchId, request).ifPresent(this.eventService::sendEvent);
        } catch (RuntimeException e) {
            logger.error("An error occurred when sending the event", e);
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "An error occurred when sending the event", e);
        }

        return new HotelAvailabilitySearchResponse(generatedSearchId);
    }
}
