package com.waProject.wap_backend.service;

import com.waProject.wap_backend.model.Apartment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface ApartmentService {
    Apartment addNewApartment (MultipartFile file, String apartmentType, String description,
                               double pricePerNight, int size, int numberOfBedrooms,
                               int numberOfBathrooms, boolean availabilityStatus) throws SQLException, IOException;
    List<Apartment> getAllApartments();

    byte[] getApartmentPhotoByApartmentId(int apartmentId) throws SQLException;

    void deleteApartment(int apartmentId);
}
