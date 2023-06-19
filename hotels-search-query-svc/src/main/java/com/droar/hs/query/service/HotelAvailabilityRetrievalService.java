package com.droar.hs.query.service;


import com.droar.hs.query.controller.dto.HotelAvailabilityRetrieveResponse;
import com.droar.hs.query.service.common.ApplicationException;

public interface HotelAvailabilityRetrievalService {
    HotelAvailabilityRetrieveResponse retrieve(final String searchId) throws ApplicationException;
}
