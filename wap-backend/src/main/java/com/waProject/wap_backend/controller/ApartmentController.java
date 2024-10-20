package com.waProject.wap_backend.controller;

import com.waProject.wap_backend.model.Apartment;
import com.waProject.wap_backend.model.BookedApartment;
import com.waProject.wap_backend.response.ApartmentResponse;
import com.waProject.wap_backend.response.BookingResponse;
import com.waProject.wap_backend.service.ApartmentService;
import com.waProject.wap_backend.service.BookingService;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.waProject.wap_backend.exception.PhotoRetrievalException;

import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("/apartments")
@CrossOrigin(origins = "*")
@RestController
public class ApartmentController {

    private final ApartmentService apartmentService;
    private final BookingService bookingService;

    public ApartmentController(ApartmentService apartmentService, BookingService bookingService) {

        this.apartmentService = apartmentService;
        this.bookingService = bookingService;
    }

    @PostMapping  // Add proper endpoint mapping
    public ResponseEntity<ApartmentResponse> addNewApartment(
            @RequestParam("photo") MultipartFile photo,
            @RequestParam("apartmentType") String apartmentType,
            @RequestParam("description") String description,
            @RequestParam("pricePerNight") double pricePerNight,
            @RequestParam("size") int size,
            @RequestParam("numberOfBedrooms") int numberOfBedrooms,
            @RequestParam("numberOfBathrooms") int numberOfBathrooms,
            @RequestParam("availabilityStatus") boolean availabilityStatus) throws SQLException, IOException {

        Apartment savedApartment = apartmentService.addNewApartment(photo, apartmentType, description,
                pricePerNight, size, numberOfBedrooms, numberOfBathrooms, availabilityStatus);

        // Create a response object
        ApartmentResponse response = new ApartmentResponse(
                savedApartment.getApartmentId(),
                savedApartment.getApartmentType(),
                savedApartment.getDescription(),
                savedApartment.getPricePerNight(),
                savedApartment.getSize(),
                savedApartment.getNumberOfBedrooms(),
                savedApartment.getNumberOfBathrooms(),
                savedApartment.isAvailabilityStatus()
        );

        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<ApartmentResponse>> getAllApartments() throws SQLException {
        List<Apartment> apartments = apartmentService.getAllApartments();
        List<ApartmentResponse> apartmentResponses = new ArrayList<>();
        for(Apartment apartment : apartments){
            byte[] photoBytes = apartmentService.getApartmentPhotoByApartmentId(apartment.getApartmentId());
            if(photoBytes != null && photoBytes.length > 0){
                String base64Photo = Base64.encodeBase64String(photoBytes);
                ApartmentResponse apartmentResponse = getApartmentResponse(apartment);
                apartmentResponse.setPhoto(base64Photo);
                apartmentResponses.add(apartmentResponse);
            }
        }
        return ResponseEntity.ok(apartmentResponses);
    }


    private ApartmentResponse getApartmentResponse(Apartment apartment) {
        List<BookedApartment> bookings = getAllBookingsByApartmentId(apartment.getApartmentId());
/*        List<BookingResponse> bookingsInfo = bookings
                .stream()
                .map(booking -> new BookingResponse(booking.getBookingId(),
                        booking.getCheckInDate(),
                        booking.getCheckOutDate(), booking.getBookingConfirmationCode())).toList();*/
        byte[] photoBytes = null;
        Blob photoBlob = apartment.getPhoto();
        if (photoBlob != null){
            try {
                photoBytes = photoBlob.getBytes(1, (int) photoBlob.length());
            }catch (SQLException e){
                throw new PhotoRetrievalException("Error retrieving photo");
            }
        }
        return new ApartmentResponse(
                apartment.getApartmentId(),
                apartment.getApartmentType(),
                apartment.getDescription(),
                apartment.getPricePerNight(),
                apartment.getSize(),
                apartment.getNumberOfBedrooms(),
                apartment.getNumberOfBathrooms(),
                apartment.isAvailabilityStatus(),
                apartment.isBooked(),
                photoBytes
                //bookingsInfo
                );
    }

    private List<BookedApartment> getAllBookingsByApartmentId(int apartmentId) {
        return bookingService.getAllBookingsByApartmentId(apartmentId);
    }

}
