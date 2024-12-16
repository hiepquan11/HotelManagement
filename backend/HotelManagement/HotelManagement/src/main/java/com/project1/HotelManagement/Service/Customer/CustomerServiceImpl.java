package com.project1.HotelManagement.Service.Customer;

import com.project1.HotelManagement.Entity.Booking;
import com.project1.HotelManagement.Entity.Customer;
import com.project1.HotelManagement.Entity.Response;
import com.project1.HotelManagement.Repository.BookingRepository;
import com.project1.HotelManagement.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public ResponseEntity<?> getBookingByCustomer(int customerId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Customer checkCustomer = customerRepository.findCustomerByCustomerId(customerId);
        if(checkCustomer == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("User not found", HttpStatus.NOT_FOUND.value()));
        }
            List<Booking> customerBooking = bookingRepository.findBookingByCustomer(checkCustomer,pageable);
            return ResponseEntity.ok().body(customerBooking);
    }

    @Override
    public ResponseEntity<?> cancelBooking(int bookingId) {
        try {
            Booking checkBooking = bookingRepository.findByBookingId(bookingId);
            if(checkBooking == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("booking not found", HttpStatus.NOT_FOUND.value()));
            }
            double cancelledFee = checkBooking.getTotalAmount();
            if(checkBooking.getBookingStatus().equals("PENDING")){
                checkBooking.setBookingStatus("CANCELLED");
                checkBooking.setCancelFee(cancelledFee);
                bookingRepository.save(checkBooking);
                return ResponseEntity.ok().body(new Response("booking cancelled", HttpStatus.OK.value()));
            }
            if(checkBooking.getBookingStatus().equals("CANCELLED")){
                return ResponseEntity.status(HttpStatus.CONFLICT).body(new Response("Booking was cancelled", HttpStatus.CONFLICT.value()));
            }
            LocalDate today = LocalDate.now();
            LocalDate localCheckInDate = checkBooking.getCheckInDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if(today.isAfter(localCheckInDate.minusDays(3))){
               cancelledFee = cancelledFee * 0.5;
                checkBooking.setBookingStatus("PENDING_CANCELLED");
                checkBooking.setCancelFee(cancelledFee);
                bookingRepository.save(checkBooking);
            }
            return ResponseEntity.ok().body(new Response("Booking cancelled with a fee of " + cancelledFee, HttpStatus.OK.value()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
}
