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

import java.util.List;

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

        Booking checkBooking = bookingRepository.findByBookingId(bookingId);
        if(checkBooking == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("booking not found", HttpStatus.NOT_FOUND.value()));
        }
        return null;
    }
}
