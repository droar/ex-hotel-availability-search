package com.droar.hs.command.domain;


import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class HotelSearchTest {

    public static final String A_SEARCH_ID = "a-search-id";
    public static final String A_HOTEL_ID = "a-hotel-id";

    public static final Instant A_CHECKIN_DATE = Instant.ofEpochMilli(100L);
    public static final Instant A_CHECKOUT_DATE = Instant.ofEpochMilli(150L);
    public static final List<Integer> AN_AGES_LIST = List.of(19, 29, 30);


    @Test
    public void availabilitySearchesAreGeneratedProperly() {
        // Given - When
        HotelSearch hotelSearch = new HotelSearch(
                A_SEARCH_ID,
                A_HOTEL_ID,
                LocalDate.ofInstant(A_CHECKIN_DATE, ZoneId.systemDefault()),
                LocalDate.ofInstant(A_CHECKOUT_DATE, ZoneId.systemDefault()),
                AN_AGES_LIST
        );

        // Then
        assertSoftly(softly -> {
            softly.assertThat(hotelSearch.searchId()).isEqualTo(A_SEARCH_ID);
            softly.assertThat(hotelSearch.hotelId()).isEqualTo(A_HOTEL_ID);
            softly.assertThat(hotelSearch.checkIn()).isEqualTo(LocalDate.ofInstant(A_CHECKIN_DATE, ZoneId.systemDefault()));
            softly.assertThat(hotelSearch.checkOut()).isEqualTo(LocalDate.ofInstant(A_CHECKOUT_DATE, ZoneId.systemDefault()));
            softly.assertThat(hotelSearch.ages()).isEqualTo(AN_AGES_LIST);
        });
    }


}