package com.droar.hs.query.service;

import com.droar.hs.query.controller.dto.HotelAvailabilityRetrieveResponse;
import com.droar.hs.query.controller.dto.RetrieveSearchResponse;
import com.droar.hs.query.dao.entities.HotelSearchJpaEntity;
import com.droar.hs.query.dao.repository.HotelSearchPostgresDataRepository;
import com.droar.hs.query.service.common.ApplicationException;
import com.droar.hs.query.service.mapper.HotelSearchJpaMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelAvailabilityRetrievalServiceImplTest {

    public static final String A_SEARCH_ID = "a-search-id";
    public static final String A_HOTEL_ID = "a-hotel-id";
    public static final Instant A_CHECKIN_DATE = Instant.ofEpochMilli(100L);
    public static final Instant A_CHECKOUT_DATE = Instant.ofEpochMilli(100L);
    public static final List<Integer> A_LIST_OF_AGES = List.of(1, 5, 7);

    @Mock
    private HotelSearchPostgresDataRepository repository;

    @Mock
    private HotelSearchJpaMapper mapper;

    @InjectMocks
    private HotelAvailabilityRetrievalServiceImpl underTest;

    @Test
    public void aRetrievalIsIssuedProperly() {
        // Given
        final HotelSearchJpaEntity anEntity = new HotelSearchJpaEntity(A_SEARCH_ID,
                A_HOTEL_ID,
                Date.from(A_CHECKIN_DATE),
                Date.from(A_CHECKOUT_DATE),
                A_LIST_OF_AGES
        );
        final Integer aCountId = 123;

        final HotelAvailabilityRetrieveResponse aResponse = new HotelAvailabilityRetrieveResponse(A_SEARCH_ID,
                new RetrieveSearchResponse(A_HOTEL_ID,
                        Date.from(A_CHECKIN_DATE),
                        Date.from(A_CHECKOUT_DATE),
                        A_LIST_OF_AGES),
                aCountId
        );


        when(this.repository.findById(A_SEARCH_ID)).thenReturn(Optional.of(anEntity));
        when(this.repository.countSimilarHotelSearches(A_HOTEL_ID, Date.from(A_CHECKIN_DATE))).thenReturn(aCountId);
        when(this.mapper.toDto(anEntity, aCountId)).thenReturn(aResponse);

        // When
        HotelAvailabilityRetrieveResponse expected = this.underTest.retrieve(A_SEARCH_ID);

        // Then
        assertSoftly(softly -> {
            softly.assertThat(expected.getSearchId()).isEqualTo(A_SEARCH_ID);
            softly.assertThat(expected.getSearch().hotelId()).isEqualTo(A_HOTEL_ID);
            softly.assertThat(expected.getSearch().checkIn()).isEqualTo(Date.from(A_CHECKIN_DATE));
            softly.assertThat(expected.getSearch().checkOut()).isEqualTo(Date.from(A_CHECKOUT_DATE));
            softly.assertThat(expected.getSearch().ages()).isEqualTo(A_LIST_OF_AGES);
            softly.assertThat(expected.getCount()).isEqualTo(aCountId);
        });
    }

    @Test
    public void notFoundSearchReturnsEmptySearch() {
        // Given
        when(this.repository.findById(A_SEARCH_ID)).thenReturn(Optional.empty());

        // When
        HotelAvailabilityRetrieveResponse expected = this.underTest.retrieve(A_SEARCH_ID);

        // Then
        assertThat(expected.getSearchId()).isNull();
        assertThat(expected.getSearch()).isNull();
    }

    @Test
    public void errorIsHandledProperly() {
        // Given
        doThrow(new RuntimeException("Ops!")).when(this.repository).findById(A_SEARCH_ID);

        // When - Then
        assertThrows(ApplicationException.class, () -> this.underTest.retrieve(A_SEARCH_ID));
    }

}