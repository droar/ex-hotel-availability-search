package com.droar.hs.command.application.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class HotelAvailabilitySearchIdGeneratorTest {

    @InjectMocks
    private HotelAvailabilitySearchIdGenerator generator;

    @Test
    public void idIsGeneratedProperly() {
        // Given - When
        final String generatedId = this.generator.generateId();

        // Then
        assertThat(generatedId).isNotEmpty();
    }

}