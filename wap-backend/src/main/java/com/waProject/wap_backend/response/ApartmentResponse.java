package com.waProject.wap_backend.response;

import com.waProject.wap_backend.model.BookedApartment;
import lombok.*;
import org.apache.tomcat.util.codec.binary.Base64;

import java.util.List;

@Data
@NoArgsConstructor
public class ApartmentResponse {
    private int apartmentId;
    private String apartmentType;
    private String description;
    private double pricePerNight;
    private int size;
    private int numberOfBedrooms;
    private int numberOfBathrooms;
    private boolean availabilityStatus;
    private boolean isBooked;
    private List<BookingResponse> bookings;
    private String photo;

    public ApartmentResponse(int apartmentId, String apartmentType, String description, double pricePerNight, int size, int numberOfBedrooms, int numberOfBathrooms, boolean availabilityStatus) {
        this.apartmentId = apartmentId;
        this.apartmentType = apartmentType;
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.size = size;
        this.numberOfBedrooms = numberOfBedrooms;
        this.numberOfBathrooms = numberOfBathrooms;
        this.availabilityStatus = availabilityStatus;
    }

    public ApartmentResponse(int apartmentId, String apartmentType, String description, double pricePerNight, int size, int numberOfBedrooms, int numberOfBathrooms, boolean availabilityStatus, boolean isBooked, byte[] photoBytes) {
        //, List<BookingResponse> bookings
        this.apartmentId = apartmentId;
        this.apartmentType = apartmentType;
        this.description = description;
        this.pricePerNight = pricePerNight;
        this.size = size;
        this.numberOfBedrooms = numberOfBedrooms;
        this.numberOfBathrooms = numberOfBathrooms;
        this.availabilityStatus = availabilityStatus;
        this.isBooked = isBooked;
        this.photo = photoBytes != null ? Base64.encodeBase64String(photoBytes) : null;
        //this.bookings = bookings;
    }
}
