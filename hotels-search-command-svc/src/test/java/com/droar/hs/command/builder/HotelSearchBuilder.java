package com.droar.hs.command.builder;

import com.droar.hs.command.domain.HotelSearch;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public final class HotelSearchBuilder {
    private String searchId;
    private String hotelId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private List<Integer> ages;

    private HotelSearchBuilder() {
    }

    private HotelSearchBuilder(final String searchId, final String hotelId, final LocalDate checkIn, final LocalDate checkOut, final List<Integer> ages) {
        this.searchId = searchId;
        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.ages = ages;
    }


    public static HotelSearchBuilder aHotelSearch() {
        return new HotelSearchBuilder();
    }
    public static HotelSearchBuilder aDefaultHotelSearch() {
        return new HotelSearchBuilder(
                "a-search-id",
                "a-hotel-id",
                LocalDate.ofInstant(Instant.ofEpochMilli(100L), ZoneId.systemDefault()),
                LocalDate.ofInstant(Instant.ofEpochMilli(150L), ZoneId.systemDefault()),
                List.of(10, 20, 30)
        );
    }

    public HotelSearchBuilder withSearchId(String searchId) {
        this.searchId = searchId;
        return this;
    }

    public HotelSearchBuilder withHotelId(String hotelId) {
        this.hotelId = hotelId;
        return this;
    }

    public HotelSearchBuilder withCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
        return this;
    }

    public HotelSearchBuilder withCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
        return this;
    }

    public HotelSearchBuilder withAges(List<Integer> ages) {
        this.ages = ages;
        return this;
    }

    public HotelSearch build() {
        return new HotelSearch(searchId, hotelId, checkIn, checkOut, ages);
    }
}
