package com.project1.HotelManagement.Controller;

import com.project1.HotelManagement.Entity.Staff;
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

    @PutMapping("/approveBooking/{staffId}/{bookingId}")
    public ResponseEntity<?> approveBooking(@PathVariable int staffId, @PathVariable int bookingId) {
        ResponseEntity<?> response = staffService.approveBooking(staffId, bookingId);
        if(response.getStatusCode() != HttpStatus.OK){
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
}
