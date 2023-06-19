package com.droar.hs.command.infrastructure.rest;

import com.droar.hs.command.application.common.ApplicationException;
import com.droar.hs.command.application.usecases.HotelAvailabilitySearchUseCase;
import com.droar.hs.command.infrastructure.rest.dto.HotelAvailabilitySearchRequest;
import com.droar.hs.command.infrastructure.rest.dto.HotelAvailabilitySearchResponse;
import com.droar.hs.command.builder.HotelAvailabilitySearchRequestBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelAvailabilityControllerTest {

    @Mock
    private HotelAvailabilitySearchResponse anyResponse;
    @Mock
    private HotelAvailabilitySearchUseCase useCase;
    @InjectMocks
    private HotelAvailabilityController underTest;

    @Test
    public void searchRequestsAreProcessedProperly() {
        // Given
        HotelAvailabilitySearchRequest aRequest = HotelAvailabilitySearchRequestBuilder.aDefaultHotelAvailabilitySearchRequest()
                .build();

        when(useCase.search(aRequest)).thenReturn(this.anyResponse);

        // When
        HotelAvailabilitySearchResponse aResponse = this.underTest.search(aRequest).getBody();

        // Then
        verify(useCase, times(1)).search(aRequest);

        assertThat(aResponse).isNotNull();
    }

    @Test
    public void applicationErrorsAreHandledProperly() {
        // Given
        HotelAvailabilitySearchRequest aRequest = HotelAvailabilitySearchRequestBuilder.aDefaultHotelAvailabilitySearchRequest()
                .build();
        Mockito.doThrow(new ApplicationException(HttpStatus.BAD_REQUEST, "Ops!")).when(this.useCase).search(aRequest);

        // When
        ResponseEntity<HotelAvailabilitySearchResponse> aResponse = this.underTest.search(aRequest);

        // Then
        assertThat(aResponse).isNotNull();
        assertThat(aResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}