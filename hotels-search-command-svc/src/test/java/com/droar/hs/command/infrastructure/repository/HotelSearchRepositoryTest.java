package com.droar.hs.command.infrastructure.repository;

import com.droar.hs.command.builder.HotelSearchBuilder;
import com.droar.hs.command.domain.HotelSearch;
import com.droar.hs.command.infrastructure.repository.entities.HotelSearchJpaEntity;
import com.droar.hs.command.infrastructure.repository.mapper.HotelSearchJpaMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelSearchRepositoryTest {

    public static final String A_SEARCH_ID = "a-search-id";
    public static final String A_HOTEL_ID = "a-hotel-id";
    public static final Instant A_CHECKIN_DATE = Instant.ofEpochMilli(100L);
    public static final Instant A_CHECKOUT_DATE = Instant.ofEpochMilli(150L);
    public static final List<Integer> AN_AGES_LIST = List.of(10, 20, 30);

    @Mock
    private HotelSearchJpaMapper mapper;
    @Mock
    private HotelSearchPostgresDataRepository repository;

    @InjectMocks
    private HotelSearchRepository underTest;

    @Test
    public void hotelSearchRequestIsSavedProperly() {
        // Given
        HotelSearch aGivenHotelSearch = HotelSearchBuilder
                .aHotelSearch()
                .withSearchId(A_SEARCH_ID)
                .withHotelId(A_HOTEL_ID)
                .withCheckIn(LocalDate.ofInstant(A_CHECKIN_DATE, ZoneId.systemDefault()))
                .withCheckOut(LocalDate.ofInstant(A_CHECKOUT_DATE, ZoneId.systemDefault()))
                .withAges(AN_AGES_LIST)
                .build();

        HotelSearchJpaEntity aGivenEntity = new HotelSearchJpaEntity(A_SEARCH_ID, A_HOTEL_ID,
                Date.from(A_CHECKIN_DATE),
                Date.from(A_CHECKOUT_DATE),
                AN_AGES_LIST);

        when(this.mapper.toJpaEntity(aGivenHotelSearch)).thenReturn(Optional.of(aGivenEntity));
        when(this.repository.save(aGivenEntity)).thenReturn(any());


        // When
        this.underTest.save(aGivenHotelSearch);

        // Then
        verify(repository).save(aGivenEntity);
    }
}