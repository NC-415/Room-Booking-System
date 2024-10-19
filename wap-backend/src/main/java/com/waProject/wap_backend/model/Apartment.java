package com.waProject.wap_backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;


import java.sql.Blob;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Apartment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int apartmentId;

    @Column(nullable = false)
    private String ApartmentType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private double pricePerNight;

    private int size;
    private int numberOfBedrooms;
    private int numberOfBathrooms;

    private boolean availabilityStatus = false;

    private boolean isBooked = false;

    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(nullable = false)
    private Timestamp updatedAt;

    @Lob
    @Column(nullable = false)
    private Blob photo;


    @OneToMany(mappedBy="apartment", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookedApartment> bookings;

    @PrePersist
    protected void onCreate() {
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        createdAt = currentTimestamp;
        updatedAt = currentTimestamp;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Timestamp(System.currentTimeMillis());
    }


    public void addBooking(BookedApartment booking){
        if (bookings == null){
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        booking.setApartment(this);
        isBooked = true;
        String bookingCode = RandomStringUtils.randomNumeric(10);
        booking.setBookingConfirmationCode(bookingCode);
    }

}
