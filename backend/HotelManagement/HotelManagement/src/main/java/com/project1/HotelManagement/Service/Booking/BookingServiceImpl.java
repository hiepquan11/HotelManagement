package com.project1.HotelManagement.Service.Booking;

import com.project1.HotelManagement.Entity.*;
import com.project1.HotelManagement.Repository.BookingRepository;
import com.project1.HotelManagement.Repository.CustomerRepository;
import com.project1.HotelManagement.Repository.RoomRepository;
import com.project1.HotelManagement.Repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BookingServiceImpl implements BookingService{

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Override
    public ResponseEntity<?> createBooking(BookingRequest bookingRequest) {
        try {
            if(bookingRequest.getCheckInDate() == null || bookingRequest.getCheckOutDate() == null){
                return ResponseEntity.badRequest().body(new Response("Checkin and checkout day must be filled", HttpStatus.BAD_REQUEST.value()));
            }

            if(bookingRequest.getQuantityCustomer() <= 0){
                return ResponseEntity.badRequest().body(new Response("Quantity customer must be filled", HttpStatus.BAD_REQUEST.value()));
            }

            if(bookingRequest.getQuantityRoom() <= 0){
                return ResponseEntity.badRequest().body(new Response("Quantity room must be greater than 0", HttpStatus.BAD_REQUEST.value()));
            }
            if(bookingRequest.getCheckInDate().after(bookingRequest.getCheckOutDate())){
                return ResponseEntity.badRequest().body(new Response("Check in date must be before check out date", HttpStatus.BAD_REQUEST.value()));
            }
            if(bookingRequest.getCustomerEmail() == null || bookingRequest.getCustomerPhoneNumber() == null || bookingRequest.getCustomerName() == null){
                return ResponseEntity.badRequest().body(new Response("Email, phone number and customer name must be fill",HttpStatus.BAD_REQUEST.value()));
            }
            if(bookingRequest.getCustomerIdentification() == null){
                return ResponseEntity.badRequest().body(new Response("Identification number must be fill", HttpStatus.BAD_REQUEST.value()));
            }

            if(bookingRequest.getRoomType() == 0){
                return ResponseEntity.badRequest().body(new Response("Room type must be selected", HttpStatus.BAD_REQUEST.value()));
            }

            RoomType checkRoomType = roomTypeRepository.findByRoomTypeId(bookingRequest.getRoomType());
            if(checkRoomType == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Room type not found", HttpStatus.NOT_FOUND.value()));
            }

            // check quantity customer fits quantity of room type
            if(bookingRequest.getQuantityCustomer() > checkRoomType.getCapacity() * bookingRequest.getQuantityRoom()){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response("Total guests exceeds capacity of room type", HttpStatus.CONFLICT.value()));
            }

            // check customer
            Customer checkCustomer = customerRepository.findByIdentificationNumber(bookingRequest.getCustomerIdentification());
            // save customer if not exist
            if(checkCustomer == null){
                checkCustomer = new Customer();
                checkCustomer.setEmail(bookingRequest.getCustomerEmail());
                checkCustomer.setIdentificationNumber(bookingRequest.getCustomerIdentification());
                checkCustomer.setPhoneNumber(bookingRequest.getCustomerPhoneNumber());
                checkCustomer.setCustomerName(bookingRequest.getCustomerName());
            }
            customerRepository.save(checkCustomer);


            // create new booking
            Booking newBooking = new Booking();
            newBooking.setBookingDate(new Date());

            // set info booking
            newBooking.setCheckInDate(bookingRequest.getCheckInDate());
            newBooking.setCheckOutDate(bookingRequest.getCheckOutDate());
            newBooking.setQuantity(bookingRequest.getQuantityCustomer());
            newBooking.setQuantityRoom(bookingRequest.getQuantityRoom());
            newBooking.setRoomType(checkRoomType.getRoomTypeId());
            newBooking.setCustomer(checkCustomer);
            newBooking.setBookingStatus("PENDING");

            // save booking
            bookingRepository.save(newBooking);
            return ResponseEntity.ok().body(new Response("Booking created successfully", HttpStatus.CREATED.value()));
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }

    }
}
