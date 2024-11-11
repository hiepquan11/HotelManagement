package com.project1.HotelManagement.Service.Staff;

import com.project1.HotelManagement.Entity.Staff;
import org.springframework.http.ResponseEntity;

public interface StaffService {
    ResponseEntity<?> approveBooking(int StaffId, int bookingId);
    ResponseEntity<?> rejectBooking(Staff staff, int bookingId);
}
