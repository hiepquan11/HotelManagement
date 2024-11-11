package com.project1.HotelManagement.Controller;

import com.project1.HotelManagement.Entity.Booking;
import com.project1.HotelManagement.Entity.BookingRequest;
import com.project1.HotelManagement.Service.Booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping("/createBooking")
    public ResponseEntity<?> addBooking(@RequestBody BookingRequest bookingRequest) {
        ResponseEntity<?> response = bookingService.createBooking(bookingRequest);
        if(response.getStatusCode() != HttpStatus.OK){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
}
