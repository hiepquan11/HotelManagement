package com.project1.HotelManagement.Repository;

import com.project1.HotelManagement.Entity.Booking;
import com.project1.HotelManagement.Entity.BookingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "bookingdetail")
public interface BookingDetailRepository extends JpaRepository<BookingDetail, Integer> {
    List<BookingDetail> findBookingDetailByBooking(Booking booking);
}
