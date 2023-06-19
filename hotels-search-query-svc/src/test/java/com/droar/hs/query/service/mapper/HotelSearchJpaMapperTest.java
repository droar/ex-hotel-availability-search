package com.droar.hs.query.service.mapper;

import com.droar.hs.query.controller.dto.HotelAvailabilityRetrieveResponse;
import com.droar.hs.query.dao.entities.HotelSearchJpaEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@ExtendWith(MockitoExtension.class)
class HotelSearchJpaMapperTest {

    public static final String A_SEARCH_ID = "a-search-id";
    public static final String A_HOTEL_ID = "a-hotel-id";
    public static final Instant A_CHECKIN_DATE = Instant.ofEpochMilli(100L);
    public static final Instant A_CHECKOUT_DATE = Instant.ofEpochMilli(100L);
    public static final List<Integer> A_LIST_OF_AGES = List.of(1, 5, 7);
    @InjectMocks
    private HotelSearchJpaMapper underTest;

    @Test
    public void entityIsMappedProperlyToResponse() {
        // Given
        final Integer aGivenCount = 5;

        final HotelSearchJpaEntity anEntity = new HotelSearchJpaEntity(A_SEARCH_ID,
                A_HOTEL_ID,
                Date.from(A_CHECKIN_DATE),
                Date.from(A_CHECKOUT_DATE),
                A_LIST_OF_AGES
        );

        // When
        HotelAvailabilityRetrieveResponse response = this.underTest.toDto(anEntity, aGivenCount);

        // Then
        assertSoftly(softly -> {
            softly.assertThat(response.getSearchId()).isEqualTo(A_SEARCH_ID);
            softly.assertThat(response.getSearch().hotelId()).isEqualTo(A_HOTEL_ID);
            softly.assertThat(response.getSearch().checkIn()).isEqualTo(Date.from(A_CHECKIN_DATE));
            softly.assertThat(response.getSearch().checkOut()).isEqualTo(Date.from(A_CHECKOUT_DATE));
            softly.assertThat(response.getSearch().ages()).isEqualTo(A_LIST_OF_AGES);
            softly.assertThat(response.getCount()).isEqualTo(aGivenCount);
        });
    }

    @Test
    public void noEntityMapsProperly() {
        // Given
        final Integer aGivenCount = 5;
        final HotelSearchJpaEntity noEntity = null;

        // When
        HotelAvailabilityRetrieveResponse response = this.underTest.toDto(noEntity, aGivenCount);

        // Then
        assertThat(response).isNull();
    }

}