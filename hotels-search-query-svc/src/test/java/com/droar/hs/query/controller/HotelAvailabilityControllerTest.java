package com.droar.hs.query.controller;

import com.droar.hs.query.controller.dto.HotelAvailabilityRetrieveResponse;
import com.droar.hs.query.service.HotelAvailabilityRetrievalService;
import com.droar.hs.query.service.common.ApplicationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HotelAvailabilityControllerTest {

    @Mock
    private HotelAvailabilityRetrieveResponse anyResponse;
    @Mock
    private HotelAvailabilityRetrievalService service;
    @InjectMocks
    private HotelAvailabilityController underTest;

    @Test
    public void countEndpointIsExecutedProperly() {
        // Given
        final String aGivenSearchId = "a-search-id";
        when(this.service.retrieve(aGivenSearchId)).thenReturn(this.anyResponse);

        // When
        HotelAvailabilityRetrieveResponse response = this.underTest.count(aGivenSearchId).getBody();

        // Then
        assertThat(response).isNotNull();
    }

    @Test
    public void applicationErrorsAreHandledProperly() {
        // Given
        final String aGivenSearchId = "a-search-id";

        Mockito.doThrow(new ApplicationException(HttpStatus.BAD_REQUEST, "Ops!")).when(this.service).retrieve(aGivenSearchId);

        // When
        ResponseEntity<HotelAvailabilityRetrieveResponse> aResponse = this.underTest.count(aGivenSearchId);

        // Then
        assertThat(aResponse).isNotNull();
        assertThat(aResponse.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }
}