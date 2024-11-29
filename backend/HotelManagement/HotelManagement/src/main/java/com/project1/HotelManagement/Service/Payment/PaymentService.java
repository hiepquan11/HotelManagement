package com.project1.HotelManagement.Service.Payment;

import com.project1.HotelManagement.Entity.Payment;
import org.springframework.http.ResponseEntity;

public interface PaymentService {
    ResponseEntity<?> createPayment(int bookingId);
}
