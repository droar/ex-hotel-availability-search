package com.droar.hs.command.infrastructure.rest;

import com.droar.hs.command.application.common.ApplicationException;
import com.droar.hs.command.application.usecases.HotelAvailabilitySearchUseCase;
import com.droar.hs.command.infrastructure.rest.dto.HotelAvailabilitySearchRequest;
import com.droar.hs.command.infrastructure.rest.dto.HotelAvailabilitySearchResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotels-availability")
public class HotelAvailabilityController {

    private final static Logger logger = LoggerFactory.getLogger(HotelAvailabilityController.class);
    private final HotelAvailabilitySearchUseCase useCase;

    public HotelAvailabilityController(final HotelAvailabilitySearchUseCase hotelAvailabilitySearchUseCase) {
        this.useCase = hotelAvailabilitySearchUseCase;
    }

    @PostMapping("/search")
    public ResponseEntity<HotelAvailabilitySearchResponse> search(@RequestBody @Validated final HotelAvailabilitySearchRequest request) {
        logger.info("HotelAvailabilityController - Executing a hotel availability search request on controller");

        try {
            return ResponseEntity.ok(this.useCase.search(request));
        } catch (ApplicationException e) {
            logger.error("Application error has happened", e);

            return ResponseEntity.status(e.getHttpStatusCode()).build();
        }
    }
}
