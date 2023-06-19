package com.droar.hs.command.application.services;

import com.droar.hs.command.application.common.ApplicationException;
import com.droar.hs.command.application.common.HotelAvailabilitySearchIdGenerator;
import com.droar.hs.command.application.mapper.HotelSearchRequestMapper;
import com.droar.hs.command.builder.HotelAvailabilitySearchRequestBuilder;
import com.droar.hs.command.builder.HotelSearchBuilder;
import com.droar.hs.command.domain.HotelSearch;
import com.droar.hs.command.domain.service.HotelSearchDomainEventService;
import com.droar.hs.command.infrastructure.rest.dto.HotelAvailabilitySearchRequest;
import com.droar.hs.command.infrastructure.rest.dto.HotelAvailabilitySearchResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelAvailabilitySearchServiceTest {

    public static final String A_GENERATED_ID = "an-uuid";

    @Mock
    private HotelSearchDomainEventService eventService;
    @Mock
    private HotelSearchRequestMapper mapper;
    @Mock
    private HotelAvailabilitySearchIdGenerator idGenerator;
    @InjectMocks
    private HotelAvailabilitySearchService underTest;

    @Test
    public void searchOperationIsExecutedProperly() {
        // Given
        HotelAvailabilitySearchRequest aRequest = HotelAvailabilitySearchRequestBuilder.aDefaultHotelAvailabilitySearchRequest().build();
        HotelSearch aDomainHotelSearch = HotelSearchBuilder.aDefaultHotelSearch().build();

        when(this.idGenerator.generateId()).thenReturn(A_GENERATED_ID);
        when(this.mapper.toDomainEntity(A_GENERATED_ID, aRequest)).thenReturn(Optional.of(aDomainHotelSearch));
        doNothing().when(this.eventService).sendEvent(aDomainHotelSearch);


        // When
        HotelAvailabilitySearchResponse response = this.underTest.search(aRequest);

        // Then
        verify(this.mapper).toDomainEntity(A_GENERATED_ID, aRequest);
        verify(this.eventService).sendEvent(aDomainHotelSearch);

        assertThat(response.searchId()).isEqualTo(A_GENERATED_ID);
    }

    @Test
    public void failureOnGenerationStopsTheWorld() {
        // Given
        HotelAvailabilitySearchRequest aRequest = HotelAvailabilitySearchRequestBuilder.aDefaultHotelAvailabilitySearchRequest().build();

        when(this.idGenerator.generateId()).thenReturn(A_GENERATED_ID);
        doThrow(new RuntimeException("Ops!")).when(this.mapper).toDomainEntity(A_GENERATED_ID, aRequest);

        // When - Then
        assertThrows(ApplicationException.class, () -> this.underTest.search(aRequest), "Ops!");

        verify(this.eventService, never()).sendEvent(any());
    }
}