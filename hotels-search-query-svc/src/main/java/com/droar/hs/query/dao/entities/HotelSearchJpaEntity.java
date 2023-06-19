package com.droar.hs.query.dao.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "HOTEL_SEARCH_AVAILABILITY")
public class HotelSearchJpaEntity {
    @Id
    @Column(name = "search_id", nullable = false)
    private String searchId;

    @Column(name = "hotel_id", nullable = false)
    private String hotelId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "check_in_date", nullable = false)
    private Date checkIn;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "check_out_date", nullable = false)
    private Date checkOut;
    @Column(name = "ages")
    @JdbcTypeCode(SqlTypes.JSON)
    private List<Integer> ages;

    public HotelSearchJpaEntity() {
    }

    public HotelSearchJpaEntity(String searchId, String hotelId, Date checkIn, Date checkOut, List<Integer> ages) {
        this.searchId = searchId;
        this.hotelId = hotelId;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.ages = ages;
    }


    @Override
    public String toString() {
        return "HotelSearchJpaEntity{" +
                "searchId='" + searchId + '\'' +
                ", hotelId='" + hotelId + '\'' +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", ages=" + ages +
                '}';
    }

    public String getSearchId() {
        return searchId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public List<Integer> getAges() {
        return ages;
    }
}
