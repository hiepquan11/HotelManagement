package com.project1.HotelManagement.Repository;

import com.project1.HotelManagement.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "booking")
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Booking findByBookingId(int bookingId);
}
