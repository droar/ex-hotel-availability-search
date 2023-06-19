package com.droar.hs.query.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class HotelAvailabilityRetrieveResponse implements Serializable {

    String searchId;
    @JsonProperty(value = "search")
    RetrieveSearchResponse search;
    Integer count;

    public HotelAvailabilityRetrieveResponse(){}

    public HotelAvailabilityRetrieveResponse(String searchId, RetrieveSearchResponse search, Integer count) {
        this.searchId = searchId;
        this.search = search;
        this.count = count;
    }

    public String getSearchId() {
        return searchId;
    }

    public RetrieveSearchResponse getSearch() {
        return search;
    }

    public Integer getCount() {
        return count;
    }
}
