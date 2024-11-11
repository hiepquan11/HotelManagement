package com.project1.HotelManagement.Service.Booking;

import com.project1.HotelManagement.Entity.Booking;
import com.project1.HotelManagement.Entity.BookingRequest;
import com.project1.HotelManagement.Entity.Customer;
import com.project1.HotelManagement.Entity.Response;
import com.project1.HotelManagement.Repository.BookingRepository;
import com.project1.HotelManagement.Repository.CustomerRepository;
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

    @Override
    public ResponseEntity<?> createBooking(BookingRequest bookingRequest) {
        try {
            if(bookingRequest.getCheckInDate() == null || bookingRequest.getCheckOutDate() == null){
                return ResponseEntity.badRequest().body(new Response("Checkin and checkout day must be filled", HttpStatus.BAD_REQUEST.value()));
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
            Customer checkCustomer = customerRepository.findByIdentificationNumber(bookingRequest.getCustomerIdentification());
            if(checkCustomer == null){
                checkCustomer = new Customer();
                checkCustomer.setEmail(bookingRequest.getCustomerEmail());
                checkCustomer.setIdentificationNumber(bookingRequest.getCustomerIdentification());
                checkCustomer.setPhoneNumber(bookingRequest.getCustomerPhoneNumber());
                checkCustomer.setCustomerName(bookingRequest.getCustomerName());
            }
            customerRepository.save(checkCustomer);
            Booking newBooking = new Booking();
            newBooking.setBookingDate(new Date());
            newBooking.setCheckInDate(bookingRequest.getCheckInDate());
            newBooking.setCheckOutDate(bookingRequest.getCheckOutDate());
            newBooking.setCustomer(checkCustomer);
            newBooking.setBookingStatus("PENDING");
            bookingRepository.save(newBooking);
            return ResponseEntity.ok(newBooking);
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }

    }
}
