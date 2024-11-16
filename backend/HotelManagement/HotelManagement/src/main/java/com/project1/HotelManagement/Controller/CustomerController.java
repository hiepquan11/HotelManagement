package com.project1.HotelManagement.Controller;

import com.project1.HotelManagement.Entity.Booking;
import com.project1.HotelManagement.Entity.Response;
import com.project1.HotelManagement.Service.Customer.CustomerService;
import com.project1.HotelManagement.Service.JWT.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private JwtService jwtService;


    @GetMapping("/{customerId}/bookings")
    public ResponseEntity<?> getCustomerBooking(@PathVariable int customerId,
                                                @RequestParam int page,
                                                @RequestParam int size,
                                                @RequestHeader("Authorization") String token) {
        try {
            token = token.substring(7);
            System.out.println("token: "+token);
            String jwtCustomerRole = jwtService.extractUserRole(token);
            Integer jwtCustomerId = jwtService.extractUserId(token);
            System.out.println("id: "+jwtCustomerId);

            if("ADMIN".equals(jwtCustomerRole) || jwtCustomerId == customerId) {
                ResponseEntity<?> response = customerService.getBookingByCustomer(customerId, page, size);
                return response;
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response("Access denied", HttpStatus.UNAUTHORIZED.value()));
            }
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error: "+ e.getMessage());
        }
    }
}
