package com.project1.HotelManagement.Service.Customer;

import com.project1.HotelManagement.Entity.Booking;
import com.project1.HotelManagement.Entity.Customer;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService{
    ResponseEntity<?> getBookingByCustomer(int customerId, int page, int size);
    ResponseEntity<?> cancelBooking(Booking booking);
}
