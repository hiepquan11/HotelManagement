package com.project1.HotelManagement.Service.Booking;

import com.project1.HotelManagement.Entity.Booking;
import com.project1.HotelManagement.Entity.BookingRequest;
import org.springframework.http.ResponseEntity;

public interface BookingService {
    ResponseEntity<?> createBooking(BookingRequest bookingRequest);
    ResponseEntity<?> getBookingDetail(int bookingId);
    ResponseEntity<?> getBookingByBookingId(int bookingId);
    ResponseEntity<?> getAllBookings(int page, int size);
    ResponseEntity<?> getInfoCustomerBooking(int bookingId);
}
