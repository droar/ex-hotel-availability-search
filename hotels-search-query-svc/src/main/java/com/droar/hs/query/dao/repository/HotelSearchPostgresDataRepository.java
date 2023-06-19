package com.droar.hs.query.dao.repository;


import com.droar.hs.query.dao.entities.HotelSearchJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface HotelSearchPostgresDataRepository extends JpaRepository<HotelSearchJpaEntity, String> {


    // Simple coincidence by hotelId and checkIn +-1 the search, could be refined more.
    @Query(nativeQuery = true,
            value = "SELECT COUNT(*) FROM HOTEL_SEARCH_AVAILABILITY H " +
                    "WHERE H.HOTEL_ID = :hotelId AND " +
                    "H.CHECK_IN_DATE BETWEEN cast(:checkIn as date) - 1 AND cast(:checkIn as date) + 1"
    )
    Integer countSimilarHotelSearches(@Param("hotelId") final String hotelId,
                                      @Param("checkIn") final Date checkIn);
}