package com.waProject.wap_backend.repository;

import com.waProject.wap_backend.model.BookedApartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookedApartmentRepository extends JpaRepository <BookedApartment, Integer> {

}
