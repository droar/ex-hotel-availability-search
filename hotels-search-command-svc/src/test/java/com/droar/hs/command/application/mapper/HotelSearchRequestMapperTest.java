package com.droar.hs.command.application.mapper;

import com.droar.hs.command.builder.HotelAvailabilitySearchRequestBuilder;
import com.droar.hs.command.domain.HotelSearch;
import com.droar.hs.command.infrastructure.rest.dto.HotelAvailabilitySearchRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class HotelSearchRequestMapperTest {
    public static final String A_SEARCH_ID = "a-search-id";
    public static final String A_HOTEL_ID = "a-hotel-id";
    public static final Instant A_CHECKIN_DATE = Instant.ofEpochMilli(100L);
    public static final Instant A_CHECKOUT_DATE = Instant.ofEpochMilli(150L);
    public static final List<Integer> AN_AGES_LIST = List.of(10, 20, 30);

    public static final String A_RANDOM_UUID = UUID.randomUUID().toString();

    @InjectMocks
    private HotelSearchRequestMapper underTest;

    @Test
    public void hotelSearchIsMappedToEntityProperly() {
        // Given
        HotelAvailabilitySearchRequest aGivenRequest = HotelAvailabilitySearchRequestBuilder
                .aDefaultHotelAvailabilitySearchRequest()
                .withHotelId(A_HOTEL_ID)
                .withCheckIn(LocalDate.ofInstant(A_CHECKIN_DATE, ZoneId.systemDefault()))
                .withCheckOut(LocalDate.ofInstant(A_CHECKOUT_DATE, ZoneId.systemDefault()))
                .withAges(AN_AGES_LIST)
                .build();

        // When
        Optional<HotelSearch> expected = this.underTest.toDomainEntity(A_RANDOM_UUID, aGivenRequest);

        // Then
        assertThat(expected).isPresent().get().satisfies(entity -> {
            assertThat(entity.searchId()).isEqualTo(A_RANDOM_UUID);
            assertThat(entity.hotelId()).isEqualTo(A_HOTEL_ID);
            assertThat(entity.checkIn()).isEqualTo(LocalDate.ofInstant(A_CHECKIN_DATE, ZoneId.systemDefault()));
            assertThat(entity.checkOut()).isEqualTo(LocalDate.ofInstant(A_CHECKOUT_DATE, ZoneId.systemDefault()));
            assertThat(entity.ages()).isEqualTo(AN_AGES_LIST);
        });
    }

    @Test
    public void emptyHotelSearchIdReturnsEmpty() {
        // Given
        HotelAvailabilitySearchRequest aGivenRequest = HotelAvailabilitySearchRequestBuilder
                .aHotelAvailabilitySearchRequest()
                .build();
        String A_NULL_SEARCH_ID = null;

        // When
        Optional<HotelSearch> expected = this.underTest.toDomainEntity(A_NULL_SEARCH_ID, aGivenRequest);

        // Then
        assertThat(expected).isNotPresent();
    }
}