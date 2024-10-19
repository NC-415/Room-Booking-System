package com.waProject.wap_backend.repository;

import com.waProject.wap_backend.model.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApartmentRepository extends JpaRepository<Apartment, Integer> {

   // List<Apartment> findByAvailabilityStatus(boolean availabilityStatus);  // Custom query method

}
