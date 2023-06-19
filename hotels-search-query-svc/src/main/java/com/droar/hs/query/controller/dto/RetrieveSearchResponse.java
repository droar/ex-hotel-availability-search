package com.droar.hs.query.controller.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public record RetrieveSearchResponse(String hotelId,
                                     Date checkIn,
                                     Date checkOut,
                                     List<Integer> ages) implements Serializable {

}