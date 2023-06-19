package com.droar.hs.command.builder;

import com.droar.hs.command.infrastructure.rest.dto.HotelAvailabilitySearchRequest;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public final class HotelAvailabilitySearchRequestBuilder {

    private String hotelId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private List<Integer> ages;


    private HotelAvailabilitySearchRequestBuilder() {
    }

    private HotelAvailabilitySearchRequestBuilder(final String hotelId, final LocalDate checkIn, final LocalDate checkOut, final List<Integer> ages) {
        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.ages = ages != null ? List.copyOf(ages) : Collections.emptyList();
    }

    public static HotelAvailabilitySearchRequestBuilder aDefaultHotelAvailabilitySearchRequest() {
        return new HotelAvailabilitySearchRequestBuilder(
                "a-hotel-id",
                LocalDate.ofInstant(Instant.ofEpochMilli(100L), ZoneId.systemDefault()),
                LocalDate.ofInstant(Instant.ofEpochMilli(150L), ZoneId.systemDefault()),
                List.of(10, 20, 30)
        );
    }

    public static HotelAvailabilitySearchRequestBuilder aHotelAvailabilitySearchRequest() {
        return new HotelAvailabilitySearchRequestBuilder();
    }

    public HotelAvailabilitySearchRequestBuilder withHotelId(String hotelId) {
        this.hotelId = hotelId;
        return this;
    }

    public HotelAvailabilitySearchRequestBuilder withCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
        return this;
    }

    public HotelAvailabilitySearchRequestBuilder withCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
        return this;
    }

    public HotelAvailabilitySearchRequestBuilder withAges(List<Integer> ages) {
        this.ages = ages;
        return this;
    }

    public HotelAvailabilitySearchRequest build() {
        return new HotelAvailabilitySearchRequest(hotelId, checkIn, checkOut, ages);
    }
}
