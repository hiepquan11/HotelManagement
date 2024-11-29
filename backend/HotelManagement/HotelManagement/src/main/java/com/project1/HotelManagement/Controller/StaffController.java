package com.project1.HotelManagement.Controller;

import com.project1.HotelManagement.Entity.Response;
import com.project1.HotelManagement.Service.Booking.BookingService;
import com.project1.HotelManagement.Service.JWT.JwtService;
import com.project1.HotelManagement.Service.Staff.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private BookingService bookingService;

    @PutMapping("/approveBooking/{staffId}/{bookingId}")
    public ResponseEntity<?> approveBooking(@PathVariable int staffId, @PathVariable int bookingId) {
       return staffService.approveBooking(staffId, bookingId);
    }

    @PutMapping("/rejectBooking/{staffId}/{bookingId}")
    public ResponseEntity<?> rejectBooking(@PathVariable int staffId, @PathVariable int bookingId) {
        return staffService.rejectBooking(staffId, bookingId);
    }

    @DeleteMapping("/deleteBooking/{bookingId}")
    public ResponseEntity<?> deleteBooking(@PathVariable int bookingId){
        return staffService.deleteBooking(bookingId);
    }

    @PutMapping("/cancelBooking/{bookingId}")
    public ResponseEntity<?> cancelBooking(@PathVariable int bookingId, @RequestHeader("Authorization") String token){
        if(token == null || token.startsWith("BEARER")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response("You must login", HttpStatus.UNAUTHORIZED.value()));
        }
        token = token.substring(7);
        String jwtStaffRole = jwtService.extractUserRole(token);
        if(jwtStaffRole == null || !jwtStaffRole.equals("ADMIN")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response("Access dined", HttpStatus.UNAUTHORIZED.value()));
        }
        return staffService.cancelBooking(bookingId);

    }
}
