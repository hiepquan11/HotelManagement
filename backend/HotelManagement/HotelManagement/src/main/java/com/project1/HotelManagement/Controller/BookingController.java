package com.project1.HotelManagement.Controller;

import com.project1.HotelManagement.Entity.Booking;
import com.project1.HotelManagement.Entity.BookingRequest;
import com.project1.HotelManagement.Service.Booking.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
public class BookingController {

    @Autowired
    private BookingService bookingService;


    @GetMapping("/getBooking/{bookingId}")
    public ResponseEntity<?> getBookingById(@PathVariable("bookingId") int bookingId) {
        return bookingService.getBookingByBookingId(bookingId);
    }

    @PostMapping("/createBooking")
    public ResponseEntity<?> addBooking(@RequestBody BookingRequest bookingRequest) {
        ResponseEntity<?> response = bookingService.createBooking(bookingRequest);
        if(response.getStatusCode() != HttpStatus.OK){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllBookings(@RequestParam int page,
                                            @RequestParam int size,
                                            @RequestHeader("Authorization") String token){
        return bookingService.getAllBookings(page, size);
    }

    @GetMapping("/getInfoCustomer/{bookingId}")
    public ResponseEntity<?> getInfoCustomer(@PathVariable("bookingId") int bookingId) {
        return bookingService.getBookingByBookingId(bookingId);
    }
}
