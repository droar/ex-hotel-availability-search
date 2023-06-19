package com.droar.hs.command.infrastructure.repository.entities;

import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class HotelSearchJpaEntityTest {

    public static final String A_SEARCH_ID = "a-search-id";
    public static final String A_HOTEL_ID = "a-hotel-id";

    public static final Date A_CHECKIN_DATE = new Date(100L);
    public static final Date A_CHECKOUT_DATE = new Date(150L);
    public static final List<Integer> AN_AGES_LIST = List.of(19, 29, 30);


    @Test
    public void jpaAvailabilitySearchesAreGeneratedProperly() {
        // Given - When
        HotelSearchJpaEntity jpaHotelSearch = new HotelSearchJpaEntity(
                A_SEARCH_ID,
                A_HOTEL_ID,
                A_CHECKIN_DATE,
                A_CHECKOUT_DATE,
                AN_AGES_LIST
        );

        // Then
        assertSoftly(softly -> {
            softly.assertThat(jpaHotelSearch.getSearchId()).isEqualTo(A_SEARCH_ID);
            softly.assertThat(jpaHotelSearch.getHotelId()).isEqualTo(A_HOTEL_ID);
            softly.assertThat(jpaHotelSearch.getCheckIn()).isEqualTo(A_CHECKIN_DATE);
            softly.assertThat(jpaHotelSearch.getCheckOut()).isEqualTo(A_CHECKOUT_DATE);
            softly.assertThat(jpaHotelSearch.getAges()).isEqualTo(AN_AGES_LIST);
        });
    }
}