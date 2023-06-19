package com.droar.hs.command.infrastructure.event;

import com.droar.hs.command.builder.HotelSearchBuilder;
import com.droar.hs.command.domain.HotelSearch;
import com.droar.hs.command.domain.repository.HotelSearchDomainRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelSearchEventConsumerTest {

    @Mock
    private HotelSearchDomainRepository repository;
    @InjectMocks
    private HotelSearchEventConsumer underTest;

    @Test
    public void eventsAreConsumedProperly() {
        // Given
        HotelSearch givenHotelSearch = HotelSearchBuilder.aDefaultHotelSearch().build();
        doNothing().when(this.repository).save(givenHotelSearch);

        // When
        this.underTest.consume(givenHotelSearch);

        // Then
        verify(this.repository).save(givenHotelSearch);
    }

    @Test
    public void errorWhenConsumingDoesNotStopTheWorld() {
        // Given
        HotelSearch givenHotelSearch = HotelSearchBuilder.aDefaultHotelSearch().build();
        doThrow(new RuntimeException("Ops!")).when(this.repository).save(givenHotelSearch);

        // When
        assertDoesNotThrow(() -> this.underTest.consume(givenHotelSearch));
    }
}