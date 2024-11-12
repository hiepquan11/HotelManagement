package com.project1.HotelManagement.Controller;

import com.project1.HotelManagement.Service.Staff.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

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
}
