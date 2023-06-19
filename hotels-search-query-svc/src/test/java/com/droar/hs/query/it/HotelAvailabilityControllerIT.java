package com.droar.hs.query.it;

import com.droar.hs.query.dao.entities.HotelSearchJpaEntity;
import com.droar.hs.query.dao.repository.HotelSearchPostgresDataRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class HotelAvailabilityControllerIT extends BaseIT {

    public static final String A_SEARCH_ID = "an-it-search-id";
    public static final String A_HOTEL_ID = "an-it-hotel-id";
    public static final Instant A_CHECKIN_DATE = Instant.ofEpochMilli(100L);
    public static final Instant A_CHECKOUT_DATE = Instant.ofEpochMilli(150L);
    public static final List<Integer> AN_AGES_LIST = List.of(10, 20, 30);

    @Autowired
    private HotelSearchPostgresDataRepository repository;

    @BeforeEach
    public void setUp() {
        HotelSearchJpaEntity anEntity = new HotelSearchJpaEntity(A_SEARCH_ID,
                A_HOTEL_ID,
                Date.from(A_CHECKIN_DATE),
                Date.from(A_CHECKOUT_DATE),
                AN_AGES_LIST);

        repository.save(anEntity);
    }


    @Test
    public void smokeTest() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/hotels-availability/count")
                                .accept(MediaType.APPLICATION_JSON)
                                .queryParam("searchId", A_SEARCH_ID)
                )
                .andExpect(status().isOk());
    }

}
