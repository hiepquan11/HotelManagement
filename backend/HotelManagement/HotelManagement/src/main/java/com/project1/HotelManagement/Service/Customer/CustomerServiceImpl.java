package com.project1.HotelManagement.Service.Customer;

import com.project1.HotelManagement.Entity.*;
import com.project1.HotelManagement.Repository.BookingRepository;
import com.project1.HotelManagement.Repository.CustomerRepository;
import com.project1.HotelManagement.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public ResponseEntity<?> getBookingByCustomer(int customerId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("bookingId")));
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
            if (checkBooking == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new Response("Booking not found", HttpStatus.NOT_FOUND.value()));
            }

            double cancelledFee = checkBooking.getTotalAmount();

            // Kiểm tra trạng thái "PENDING"
            if (checkBooking.getBookingStatus().equals("PENDING")) {
                checkBooking.setBookingStatus("CANCELLED");
                checkBooking.setCancelFee(cancelledFee);
                updateStatusRoom(checkBooking);
                bookingRepository.save(checkBooking);
                return ResponseEntity.ok().body(new Response("Booking cancelled with 100% refund", HttpStatus.OK.value()));
            }

            // Kiểm tra nếu booking đã bị hủy
            if (checkBooking.getBookingStatus().equals("CANCELLED")) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new Response("Booking was already cancelled", HttpStatus.CONFLICT.value()));
            }

            // Kiểm tra ngày hủy booking
            LocalDate today = LocalDate.now();
            if (checkBooking.getCheckInDate() != null) {
                LocalDate localCheckInDate = checkBooking.getCheckInDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                // Nếu hủy trước 3 ngày thì hoàn 100%
                if (today.isBefore(localCheckInDate.minusDays(3))) {
                    checkBooking.setBookingStatus("CANCELLED");
                    checkBooking.setCancelFee(cancelledFee);
                    updateStatusRoom(checkBooking);
                    bookingRepository.save(checkBooking);
                    return ResponseEntity.ok().body(new Response("Booking cancelled with 100% refund", HttpStatus.OK.value()));
                }
                // Nếu hủy sau 3 ngày thì hoàn 30%
                else {
                    cancelledFee = cancelledFee * 0.3; // Hoàn 30% phí
                    checkBooking.setBookingStatus("CANCELLED");
                    checkBooking.setCancelFee(cancelledFee);
                    updateStatusRoom(checkBooking);
                    bookingRepository.save(checkBooking);
                    return ResponseEntity.ok().body(new Response("Booking cancelled with a 30% refund. Fee: " + cancelledFee, HttpStatus.OK.value()));
                }


            }
            // Trả về khi không đủ điều kiện hủy
            return ResponseEntity.ok().body(new Response("Booking cancellation is not allowed at this time", HttpStatus.OK.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }

    private void updateStatusRoom(Booking booking){
        List<BookingDetail> bookingDetails = booking.getBookingDetails();
        if(!bookingDetails.isEmpty()){
            for(BookingDetail bookingDetail : bookingDetails){
                Room room = bookingDetail.getRoom();
                if(room != null){
                    room.setStatus("AVAILABLE");
                    roomRepository.save(room);
                }
            }
        }
    }

}
