package com.waProject.wap_backend.response;

import lombok.*;

import java.sql.Timestamp;

@Data
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
                           String bookingConfirmationCode) {
        this.bookingId = bookingId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.bookingConfirmationCode = bookingConfirmationCode;
    }
}
