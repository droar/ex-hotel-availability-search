package com.droar.hs.command.infrastructure.event;

import com.droar.hs.command.builder.HotelSearchBuilder;
import com.droar.hs.command.domain.HotelSearch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;

import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HotelSearchEventProducerTest {

    public static final String A_TOPIC_NAME = "a-topic-name";

    @Mock
    private KafkaTemplate<String, Object> producer;

    @InjectMocks
    private HotelSearchEventProducer underTest;

    @BeforeEach
    public void setUp(){
        ReflectionTestUtils.setField(this.underTest, "topicName", A_TOPIC_NAME);
    }
    @Test
    public void eventIsSentProperly() throws JsonProcessingException {
        // Given
        HotelSearch givenHotelSearch = HotelSearchBuilder.aDefaultHotelSearch().build();
        when(this.producer.send(A_TOPIC_NAME, givenHotelSearch)).thenReturn(mock(CompletableFuture.class));

        // When
        this.underTest.sendEvent(givenHotelSearch);

        // Then
        verify(this.producer).send(A_TOPIC_NAME, givenHotelSearch);
    }

}