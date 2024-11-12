package com.project1.HotelManagement.Service.Staff;

import com.project1.HotelManagement.Entity.Booking;
import com.project1.HotelManagement.Entity.Response;
import com.project1.HotelManagement.Entity.Room;
import com.project1.HotelManagement.Entity.Staff;
import com.project1.HotelManagement.Repository.BookingRepository;
import com.project1.HotelManagement.Repository.RoomRepository;
import com.project1.HotelManagement.Repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class StaffServiceImpl implements StaffService{

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public ResponseEntity<?> approveBooking(int staffId, int bookingId) {
        try {
            Staff staff = staffRepository.findByStaffId(staffId);
            if(staff == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Staff is not exist",HttpStatus.NOT_FOUND.value()));
            }
            Booking checkBooking = bookingRepository.findByBookingId(bookingId);
            if(checkBooking == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Booking is not found", HttpStatus.NOT_FOUND.value()));
            }
            List<Room> availableRoom = roomRepository.findByStatus("Available");
            if(availableRoom.isEmpty()){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response("No room are available",HttpStatus.CONFLICT.value()));
            }
            Random random = new Random();
            Room selectedRoom = availableRoom.get(random.nextInt(availableRoom.size()));
            checkBooking.setBookingStatus("APPROVE");
            checkBooking.setStaff(staff);
            checkBooking.setRoom(selectedRoom);
            bookingRepository.save(checkBooking);
            return ResponseEntity.ok(new Response("Booking approved", HttpStatus.OK.value()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ErrorL "+e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> rejectBooking(int staffId, int bookingId) {
        try {
            Staff checkStaff = staffRepository.findByStaffId(staffId);
            if(checkStaff == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Staff is not exist",HttpStatus.NOT_FOUND.value()));
            }
            Booking checkBooking = bookingRepository.findByBookingId(bookingId);
            if(checkBooking == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Booking is not found", HttpStatus.NOT_FOUND.value()));
            }
            checkBooking.setBookingStatus("REJECT");
            checkBooking.setStaff(checkStaff);
            bookingRepository.save(checkBooking);
            return ResponseEntity.ok(new Response("Booking rejected", HttpStatus.OK.value()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: "+e.getMessage());
        }

    }

    @Override
    public ResponseEntity<?> deleteBooking(int bookingId) {
        Booking checkBooking = bookingRepository.findByBookingId(bookingId);
        try {
            if(checkBooking == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Booking is not found", HttpStatus.NOT_FOUND.value()));
            }
            bookingRepository.delete(checkBooking);
            return ResponseEntity.ok().body(new Response("Booking deleted", HttpStatus.OK.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Response("Error",HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}
