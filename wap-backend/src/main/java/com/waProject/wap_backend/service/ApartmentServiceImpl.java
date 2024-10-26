package com.waProject.wap_backend.service;


import com.waProject.wap_backend.model.Apartment;
import com.waProject.wap_backend.model.BookedApartment;
import com.waProject.wap_backend.repository.ApartmentRepository;
import com.waProject.wap_backend.repository.BookedApartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ApartmentServiceImpl implements ApartmentService{

    @Autowired
    private ApartmentRepository apartmentRepository;

    @Autowired
    private BookedApartmentRepository bookedApartmentRepository;

    // Method to add a new apartment
    @Override
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


    @Override
    public List<Apartment> getAllApartments() {
        return apartmentRepository.findAll();
    }

    @Override
    public byte[] getApartmentPhotoByApartmentId(int apartmentId) throws SQLException {
        Optional<Apartment> theApartment = apartmentRepository.findById(apartmentId);
        if(theApartment.isEmpty()){
            throw new ResourceNotFoundException("Sorry, Apartment not found!");
        }
        Blob photoBlob = theApartment.get().getPhoto();
        if(photoBlob != null){
            return photoBlob.getBytes(1, (int) photoBlob.length());
        }
        return null;    }

    @Override
    public void deleteApartment(int apartmentId) {
        Optional<Apartment> theApartment = apartmentRepository.findById(apartmentId);
        if(theApartment.isPresent()){
            apartmentRepository.deleteById(apartmentId);
        }
    }
}
