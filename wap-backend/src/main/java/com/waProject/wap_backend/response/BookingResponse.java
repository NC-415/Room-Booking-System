package com.waProject.wap_backend.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingResponse {
    private Long bookingId;
    private Timestamp checkInDate;
    private Timestamp checkOutDate;
    private String guestFullName;
    private String guestEmail;
    private int numOfAdults;
    private int numOfChildren;
    private int totalNumOfGuests;
    private String bookingConfirmationCode;

    // Optional: To include apartment details in the booking response
    private ApartmentResponse apartment;

    // Constructor without ApartmentResponse (if not needed)
    public BookingResponse(Long bookingId, Timestamp checkInDate, Timestamp checkOutDate,
                           String guestFullName, String guestEmail, int numOfAdults,
                           int numOfChildren, int totalNumOfGuests, String bookingConfirmationCode) {
        this.bookingId = bookingId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.guestFullName = guestFullName;
        this.guestEmail = guestEmail;
        this.numOfAdults = numOfAdults;
        this.numOfChildren = numOfChildren;
        this.totalNumOfGuests = totalNumOfGuests;
        this.bookingConfirmationCode = bookingConfirmationCode;
    }
}
