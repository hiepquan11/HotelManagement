package com.project1.HotelManagement.Repository;

import com.project1.HotelManagement.Entity.Booking;
import com.project1.HotelManagement.Entity.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "booking")
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Booking findByBookingId(int bookingId);
    List<Booking> findBookingByCustomer(Customer customer, Pageable pageable);
}
