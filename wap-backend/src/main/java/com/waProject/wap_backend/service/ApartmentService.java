package com.waProject.wap_backend.service;


import com.waProject.wap_backend.model.Apartment;
import com.waProject.wap_backend.model.BookedApartment;
import com.waProject.wap_backend.repository.ApartmentRepository;
import com.waProject.wap_backend.repository.BookedApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ApartmentService {

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private BookedApartmentRepository bookedApartmentRepository;

    // Method to add a new apartment
    public Apartment addNewApartment(MultipartFile file, String apartmentType, String description,
                                     double pricePerNight, int size, int numberOfBedrooms,
                                     int numberOfBathrooms, boolean availabilityStatus) throws SQLException, IOException {

        Apartment apartment = new Apartment();
        apartment.setApartmentType(apartmentType);
        apartment.setDescription(description);
        apartment.setPricePerNight(pricePerNight);
        apartment.setSize(size);
        apartment.setNumberOfBedrooms(numberOfBedrooms);
        apartment.setNumberOfBathrooms(numberOfBathrooms);
        apartment.setAvailabilityStatus(availabilityStatus);

        // Handle the photo upload
        if (!file.isEmpty()) {
            byte[] photoBytes = file.getBytes();
            Blob photoBlob = new SerialBlob(photoBytes);
            apartment.setPhoto(photoBlob);
        }

        return apartmentRepository.save(apartment);
    }


    public Apartment updateApartment(int id, Apartment updatedApartment) {
        Optional<Apartment> existingApartment = apartmentRepository.findById(id);
        if (existingApartment.isPresent()) {
            Apartment apartment = existingApartment.get();
            apartment.setApartmentType(updatedApartment.getApartmentType());
            apartment.setDescription(updatedApartment.getDescription());
            apartment.setPricePerNight(updatedApartment.getPricePerNight());
            apartment.setSize(updatedApartment.getSize());
            apartment.setNumberOfBedrooms(updatedApartment.getNumberOfBedrooms());
            apartment.setNumberOfBathrooms(updatedApartment.getNumberOfBathrooms());
            apartment.setAvailabilityStatus(updatedApartment.isAvailabilityStatus());
            apartment.setPhoto(updatedApartment.getPhoto());
            return apartmentRepository.save(apartment);
        }
        return null;
    }

    public BookedApartment bookApartment(BookedApartment booking, int apartmentId) {
        Optional<Apartment> apartment = apartmentRepository.findById(apartmentId);
        if (apartment.isPresent()) {
            Apartment apt = apartment.get();
            apt.addBooking(booking);
            apartmentRepository.save(apt);
            return bookedApartmentRepository.save(booking);
        }
        return null;
    }

}
