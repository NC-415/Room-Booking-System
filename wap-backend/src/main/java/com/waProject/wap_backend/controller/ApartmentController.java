package com.waProject.wap_backend.controller;

import com.waProject.wap_backend.model.Apartment;
import com.waProject.wap_backend.response.ApartmentResponse;
import com.waProject.wap_backend.service.ApartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class ApartmentController {

    private final ApartmentService apartmentService;

    public ApartmentController(ApartmentService apartmentService) {

        this.apartmentService = apartmentService;
    }

    @PostMapping("/apartments")  // Add proper endpoint mapping
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


}
