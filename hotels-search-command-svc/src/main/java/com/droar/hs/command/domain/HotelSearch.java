package com.droar.hs.command.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public record HotelSearch(String searchId,
                          String hotelId,
                          LocalDate checkIn,
                          LocalDate checkOut,
                          List<Integer> ages) implements Serializable {
    @Override
    public String toString() {
        return "HotelSearch{" +
                "searchId='" + searchId + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", ages=" + ages +
                '}';
    }
}
