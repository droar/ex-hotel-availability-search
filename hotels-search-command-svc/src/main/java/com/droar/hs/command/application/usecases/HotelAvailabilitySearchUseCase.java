package com.droar.hs.command.application.usecases;

import com.droar.hs.command.application.common.ApplicationException;
import com.droar.hs.command.infrastructure.rest.dto.HotelAvailabilitySearchRequest;
import com.droar.hs.command.infrastructure.rest.dto.HotelAvailabilitySearchResponse;

public interface HotelAvailabilitySearchUseCase {
    HotelAvailabilitySearchResponse search(final HotelAvailabilitySearchRequest request) throws ApplicationException;
}
