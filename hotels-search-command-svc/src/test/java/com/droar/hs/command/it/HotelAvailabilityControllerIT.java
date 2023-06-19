package com.droar.hs.command.it;

import com.droar.hs.command.infrastructure.event.HotelSearchEventProducer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HotelAvailabilityControllerIT extends BaseIT {

    @SpyBean
    private HotelSearchEventProducer producer;

    @Test
    public void smokeTest() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/hotels-availability/search")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(new MediaType("application", "json", StandardCharsets.UTF_8))
                                .content("{\n" +
                                        "    \"hotelId\": \"test-hotel-id-1\",\n" +
                                        "    \"checkIn\": \"2023-12-25\",\n" +
                                        "    \"checkOut\": \"2023-12-28\",\n" +
                                        "    \"ages\": [\n" +
                                        "        30,\n" +
                                        "        29,\n" +
                                        "        1,\n" +
                                        "        3\n" +
                                        "    ]\n" +
                                        "}")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.searchId").isNotEmpty());

        verify(this.producer).sendEvent(any());
    }

}
