package com.project1.HotelManagement.Controller;

import com.project1.HotelManagement.Entity.*;
import com.project1.HotelManagement.Repository.*;
import com.project1.HotelManagement.Service.Email.EmailServiceImpl;
import com.project1.HotelManagement.Service.Payment.PaymentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping( "/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private EmailServiceImpl emailServiceImpl;
    @Autowired
    private BookingDetailRepository bookingDetailRepository;


    @GetMapping("/createPayment")
    public ResponseEntity<?> createPayment(@RequestParam int bookingId) {
        return paymentService.createPayment(bookingId);
    }

    @Transactional
    @GetMapping("/transaction")
    public ResponseEntity<?> transactions(
            @RequestParam(value = "vnp_Amount") String amount,
            @RequestParam(value = "vnp_BankCode") String bankCode,
            @RequestParam(value = "vnp_OrderInfo") String order,
            @RequestParam(value = "vnp_ResponseCode") String responseCode) {

        try {
            // Validate and convert amount
            double convertAmount = Double.parseDouble(amount) / 100;

            // Check payment response code
            if ("00".equals(responseCode)) {
                // Parse booking ID
                int bookingId = Integer.parseInt(order);

                // Find booking
                Booking checkBooking = bookingRepository.findByBookingId(bookingId);
                if (checkBooking == null) {
                    return ResponseEntity.badRequest().body("Booking not found.");
                }

                // set status payment
                checkBooking.setBookingStatus("APPROVE");

                // set room for booking
                List<BookingDetail> bookingDetails = new ArrayList<>();
                int roomTypeId = checkBooking.getRoomType();
                RoomType roomType = roomTypeRepository.findByRoomTypeId(roomTypeId);
                List<Room> availableRooms = roomRepository.findByRoomTypeAndStatus(roomType, "AVAILABLE");

                // Kiểm tra danh sách trống
                if (availableRooms.isEmpty()) {
                    return ResponseEntity.badRequest().body("No available rooms for the selected room type.");
                }

                // Kiểm tra số lượng phòng sẵn có
                if (availableRooms.size() < checkBooking.getQuantityRoom()) {
                    return ResponseEntity.badRequest().body("Not enough available rooms for the booking.");
                }

                TransactionResponse response = new TransactionResponse();

                // Tiếp tục xử lý nếu đủ phòng
                List<String> roomNumbers = new ArrayList<>();
                for (int i = 0; i < checkBooking.getQuantityRoom(); i++) {
                    Room room = availableRooms.get(i);
                    room.setStatus("BOOKED");
                    roomRepository.save(room);

                    BookingDetail bookingDetail = new BookingDetail();
                    bookingDetail.setBooking(checkBooking);
                    bookingDetail.setRoom(room);
                    bookingDetail.setPrice(room.getRoomType().getPrice());
                    bookingDetails.add(bookingDetail);
                    bookingDetailRepository.save(bookingDetail);

                    roomNumbers.add(room.getRoomNumber());
                }

                response.setRoomNumber(roomNumbers);

                checkBooking.setBookingDetails(bookingDetails);
                bookingRepository.save(checkBooking);

                // Prepare payment object
                Payment checkPayment = checkBooking.getPayment();
                if(checkPayment == null) {
                    Payment payment = new Payment();
                    payment.setPaymentAmount(convertAmount);
                    payment.setCustomer(checkBooking.getCustomer());
                    payment.setBooking(checkBooking);
                    payment.setStatus("PAYMENT_SUCCESS");
                    payment.setPaymentMethod("BANKING");
                    payment.setPaymentDate(new Date());
                    paymentRepository.save(payment);
                }

                // Prepare response

                response.setResponseCode("Payment Successful");
                response.setAmount(convertAmount);
                response.setOrderInfo(order);
                response.setBankCode(bankCode);

                Context context = new Context();
                context.setVariable("bookingId", bookingId);
                context.setVariable("customerName", checkBooking.getCustomer().getCustomerName());
                context.setVariable("amount", convertAmount);
                String roomNumberString = String.join(", ", roomNumbers);
                context.setVariable("roomNumber", roomNumberString);
                context.setVariable("checkInDate", checkBooking.getCheckInDate());
                context.setVariable("checkOutDate", checkBooking.getCheckOutDate());

                emailServiceImpl.sendEmail(
                        checkBooking.getCustomer().getEmail(),
                        "Payment Info",
                        "InfoBooking",
                        context

                );

                return ResponseEntity.ok(response);
            }

            return ResponseEntity.badRequest().body("Payment failed. Invalid response code.");
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid amount or order ID format.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }
}
