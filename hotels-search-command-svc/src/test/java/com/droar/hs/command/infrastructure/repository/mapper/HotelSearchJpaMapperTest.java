package com.droar.hs.command.infrastructure.repository.mapper;

import com.droar.hs.command.builder.HotelSearchBuilder;
import com.droar.hs.command.domain.HotelSearch;
import com.droar.hs.command.infrastructure.repository.entities.HotelSearchJpaEntity;
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

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class HotelSearchJpaMapperTest {

    public static final String A_SEARCH_ID = "a-search-id";
    public static final String A_HOTEL_ID = "a-hotel-id";
    public static final LocalDate A_CHECKIN_DATE = LocalDate.ofInstant(Instant.ofEpochMilli(100L), ZoneId.systemDefault());
    public static final LocalDate A_CHECKOUT_DATE = LocalDate.ofInstant(Instant.ofEpochMilli(150L), ZoneId.systemDefault());
    public static final List<Integer> AN_AGES_LIST = List.of(10, 20, 30);

    @InjectMocks
    private HotelSearchJpaMapper underTest;

    @Test
    public void hotelSearchIsMappedToEntityProperly() {
        // Given
        HotelSearch aGivenHotelSearch = HotelSearchBuilder
                .aHotelSearch()
                .withSearchId(A_SEARCH_ID)
                .withHotelId(A_HOTEL_ID)
                .withCheckIn(A_CHECKIN_DATE)
                .withCheckOut(A_CHECKOUT_DATE)
                .withAges(AN_AGES_LIST)
                .build();

        // When
        Optional<HotelSearchJpaEntity> expected = this.underTest.toJpaEntity(aGivenHotelSearch);

        // Then
        assertThat(expected).isPresent().get().satisfies(entity -> {
            assertThat(entity.getSearchId()).isEqualTo(A_SEARCH_ID);
            assertThat(entity.getHotelId()).isEqualTo(A_HOTEL_ID);
            assertThat(entity.getCheckIn()).isEqualTo(Date.from(A_CHECKIN_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            assertThat(entity.getCheckOut()).isEqualTo(Date.from(A_CHECKOUT_DATE.atStartOfDay(ZoneId.systemDefault()).toInstant()));
            assertThat(entity.getAges()).isEqualTo(AN_AGES_LIST);
        });
    }

    @Test
    public void emptyHotelSearchIdReturnsEmpty() {
        // Given
        HotelSearch aGivenHotelSearch = HotelSearchBuilder
                .aHotelSearch()
                .withSearchId(null)
                .build();

        // When
        Optional<HotelSearchJpaEntity> expected = this.underTest.toJpaEntity(aGivenHotelSearch);

        // Then
        assertThat(expected).isNotPresent();
    }
}