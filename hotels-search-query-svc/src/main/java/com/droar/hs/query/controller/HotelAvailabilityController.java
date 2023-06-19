package com.droar.hs.query.controller;


import com.droar.hs.query.controller.dto.HotelAvailabilityRetrieveResponse;
import com.droar.hs.query.service.HotelAvailabilityRetrievalService;
import com.droar.hs.query.service.common.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hotels-availability")
public class HotelAvailabilityController {

    private final static Logger logger = LoggerFactory.getLogger(HotelAvailabilityController.class);

    private final HotelAvailabilityRetrievalService service;

    public HotelAvailabilityController(final HotelAvailabilityRetrievalService hotelAvailabilityRetrievalService) {
        this.service = hotelAvailabilityRetrievalService;
    }

    @GetMapping("/count")
    public ResponseEntity<HotelAvailabilityRetrieveResponse> count(@RequestParam("searchId") @Validated final String searchId) {
        logger.info("HotelAvailabilityController - Executing a hotel availability retrieval request Count on controller");

        try {
            return ResponseEntity.ok(this.service.retrieve(searchId));
        } catch (ApplicationException e) {
            logger.error("Application error has happened", e);

            return ResponseEntity.status(e.getHttpStatusCode()).build();
        }
    }
}
