package com.waProject.wap_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name = "BookedApartment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BookedApartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @Column(nullable = false)
    private Timestamp checkInDate;

    @Column(nullable = false)
    private Timestamp checkOutDate;

    @Column(nullable = false)
    private String guestFullName;

    @Column(nullable = false)
    private String guestEmail;

    private int numOfAdults;
    private int numOfChildren;

    @Column(nullable = false)
    private int totalNumOfGuests;

    @Column(name = "confirmation_Code")
    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "apartment_id")
    private Apartment apartment;

    @PrePersist
    protected void calculateTotalNumOfGuests() {
        this.totalNumOfGuests = this.numOfAdults + this.numOfChildren;
    }

    public void setApartment(Apartment apartment) {
    }

}
