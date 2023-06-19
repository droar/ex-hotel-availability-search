package com.droar.hs.command.infrastructure.rest.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record HotelAvailabilitySearchRequest(
        String hotelId,
        LocalDate checkIn,
        LocalDate checkOut,
        List<Integer> ages) implements Serializable {

    public HotelAvailabilitySearchRequest(String hotelId,

                                          LocalDate checkIn,
                                          LocalDate checkOut,
                                          List<Integer> ages) {
        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.ages = ages != null ? List.copyOf(ages) : Collections.emptyList();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HotelAvailabilitySearchRequest that = (HotelAvailabilitySearchRequest) o;
        return Objects.equals(hotelId, that.hotelId) && Objects.equals(checkIn, that.checkIn) && Objects.equals(checkOut, that.checkOut) && Objects.equals(ages, that.ages);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hotelId, checkIn, checkOut, ages);
    }

    @Override
    public String toString() {
        return "HotelAvailabilitySearchRequest{" +
                "hotelId='" + hotelId + '\'' +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", ages=" + ages +
                '}';
    }
}
