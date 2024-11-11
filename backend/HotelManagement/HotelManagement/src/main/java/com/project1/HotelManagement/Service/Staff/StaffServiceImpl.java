package com.project1.HotelManagement.Service.Staff;

import com.project1.HotelManagement.Entity.Booking;
import com.project1.HotelManagement.Entity.Response;
import com.project1.HotelManagement.Entity.Staff;
import com.project1.HotelManagement.Repository.BookingRepository;
import com.project1.HotelManagement.Repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StaffServiceImpl implements StaffService{

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private StaffRepository staffRepository;

    @Override
    public ResponseEntity<?> approveBooking(int staffId, int bookingId) {
        Staff staff = staffRepository.findByStaffId(staffId);
        if(staff == null) {
            return ResponseEntity.badRequest().body(new Response("Staff is not exist",HttpStatus.NOT_FOUND.value()));
        }
        Booking checkBooking = bookingRepository.findByBookingId(bookingId);
        if(checkBooking == null) {
            return ResponseEntity.badRequest().body(new Response("Booking is not found", HttpStatus.NOT_FOUND.value()));
        }
        checkBooking.setBookingStatus("APPROVE");
        checkBooking.setStaff(staff);
        bookingRepository.save(checkBooking);
        return ResponseEntity.ok(new Response("Booking approved", HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<?> rejectBooking(Staff staff, int bookingId) {
        return null;
    }
}
