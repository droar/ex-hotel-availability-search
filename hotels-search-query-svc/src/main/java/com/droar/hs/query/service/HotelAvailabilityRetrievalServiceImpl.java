package com.droar.hs.query.service;


import com.droar.hs.query.controller.dto.HotelAvailabilityRetrieveResponse;
import com.droar.hs.query.dao.repository.HotelSearchPostgresDataRepository;
import com.droar.hs.query.service.common.ApplicationException;
import com.droar.hs.query.service.mapper.HotelSearchJpaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
class HotelAvailabilityRetrievalServiceImpl implements HotelAvailabilityRetrievalService {

    private final static Logger logger = LoggerFactory.getLogger(HotelAvailabilityRetrievalServiceImpl.class);

    private final HotelSearchPostgresDataRepository dataRepository;

    private final HotelSearchJpaMapper mapper;

    public HotelAvailabilityRetrievalServiceImpl(final HotelSearchPostgresDataRepository dataRepository,
                                                 final HotelSearchJpaMapper hotelSearchJpaMapper) {
        this.dataRepository = dataRepository;
        this.mapper = hotelSearchJpaMapper;
    }

    @Override
    public HotelAvailabilityRetrieveResponse retrieve(String searchId) throws ApplicationException {
        try {
            logger.info("Retrieving searchId {} from repository", searchId);

            return this.dataRepository.findById(searchId)
                    .map(it -> {
                        Integer countSimilar = this.dataRepository.countSimilarHotelSearches(it.getHotelId(), it.getCheckIn());
                        return this.mapper.toDto(it, countSimilar);
                    })
                    .orElse(new HotelAvailabilityRetrieveResponse());
        } catch (RuntimeException e) {
            logger.error("An error has occurred retrieving searches from repository: {}", e.getMessage());
            throw new ApplicationException(HttpStatus.BAD_REQUEST, "An error has occurred retrieving searches", e);
        }
    }
}