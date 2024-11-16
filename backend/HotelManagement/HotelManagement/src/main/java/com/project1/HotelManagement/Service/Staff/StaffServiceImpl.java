package com.project1.HotelManagement.Service.Staff;

import com.project1.HotelManagement.Entity.*;
import com.project1.HotelManagement.Repository.BookingRepository;
import com.project1.HotelManagement.Repository.RoomRepository;
import com.project1.HotelManagement.Repository.RoomTypeRepository;
import com.project1.HotelManagement.Repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Override
    public ResponseEntity<?> approveBooking(int staffId, int bookingId) {
        Staff checkStaff = staffRepository.findByStaffId(staffId);
        if(checkStaff == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Staff not found",HttpStatus.NOT_FOUND.value()));
        }
        Booking checkBooking = bookingRepository.findByBookingId(bookingId);
        if(checkBooking == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Booking not found",HttpStatus.NOT_FOUND.value()));
        }



        // find available rooms
        List<Room> availableRoom = roomRepository.findByStatus("Available");
        if(availableRoom.size() < checkBooking.getQuantity()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response("Not enough room",HttpStatus.CONFLICT.value()));
        }

        // select room for booking
        List<BookingDetail> bookingDetails = new ArrayList<>();
        for(int i = 0; i < checkBooking.getQuantityRoom(); i++){
            Room room = availableRoom.get(i);
            room.setStatus("BOOKED");
            roomRepository.save(room);

            BookingDetail bookingDetail = new BookingDetail();
            bookingDetail.setBooking(checkBooking);
            bookingDetail.setRoom(room);
            bookingDetails.add(bookingDetail);
        }

        checkBooking.setBookingStatus("APPROVE");
        checkBooking.setStaff(checkStaff);
        checkBooking.setBookingDetails(bookingDetails);
        bookingRepository.save(checkBooking);
        return ResponseEntity.ok().body(new Response("Booking approved",HttpStatus.OK.value()));
    }

    @Override
    public ResponseEntity<?> rejectBooking(int staffId, int bookingId) {
        return null;
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
