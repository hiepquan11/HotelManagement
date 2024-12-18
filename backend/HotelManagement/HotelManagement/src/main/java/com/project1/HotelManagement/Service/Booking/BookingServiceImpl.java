package com.project1.HotelManagement.Service.Booking;

import com.project1.HotelManagement.Entity.*;
import com.project1.HotelManagement.Repository.*;
import com.project1.HotelManagement.Service.Email.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService{

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private EmailServiceImpl emailServiceImpl;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookingDetailRepository bookingDetailRepository;

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

            if(bookingRequest.getCheckInDate().before(new Date())){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Check in date must be before now", HttpStatus.BAD_REQUEST.value()));
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
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new Response("Total guests exceeds capacity of room type", HttpStatus.UNPROCESSABLE_ENTITY.value()));
            }
            List<Room> checkRooms = roomRepository.findByRoomTypeAndStatus(checkRoomType, "AVAILABLE");
            if(checkRooms.isEmpty() || checkRooms.size() < bookingRequest.getQuantityCustomer()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("There are no booking rooms available", HttpStatus.BAD_REQUEST.value()));
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

            // calculate total amount
            double totalAmount = bookingRequest.getQuantityRoom() * checkRoomType.getPrice();

            // set info booking
            newBooking.setCheckInDate(bookingRequest.getCheckInDate());
            newBooking.setCheckOutDate(bookingRequest.getCheckOutDate());
            newBooking.setQuantity(bookingRequest.getQuantityCustomer());
            newBooking.setQuantityRoom(bookingRequest.getQuantityRoom());
            newBooking.setRoomType(checkRoomType.getRoomTypeId());
            newBooking.setTotalAmount(totalAmount);
            newBooking.setCustomer(checkCustomer);
            newBooking.setBookingStatus("PENDING");

            // save booking
            bookingRepository.save(newBooking);

            // sending email
            Context context = new Context();
            context.setVariable("customerName", bookingRequest.getCustomerName());
            context.setVariable("bookingId", newBooking.getBookingId());
            context.setVariable("checkInDate", bookingRequest.getCheckInDate());
            context.setVariable("checkOutDate", bookingRequest.getCheckOutDate());
            context.setVariable("roomType", checkRoomType.getRoomTypeName());
            context.setVariable("quantityCustomer", bookingRequest.getQuantityCustomer());
            context.setVariable("quantityRoom", bookingRequest.getQuantityRoom());
            context.setVariable("totalAmount", totalAmount);

            emailServiceImpl.sendEmail(
                    bookingRequest.getCustomerEmail(),
                    "Xác nhận đặt phòng",
                    "bookingConfirm",
                    context
            );

            return ResponseEntity.ok().body(new Response("Booking created successfully", HttpStatus.CREATED.value()));
        } catch (Exception e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> getBookingDetail(int bookingId) {
        Booking checkBooking = bookingRepository.findByBookingId(bookingId);
        if(checkBooking == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Booking not found", HttpStatus.NOT_FOUND.value()));
        }
        List<BookingDetail> bookingDetails = bookingDetailRepository.findBookingDetailByBooking(checkBooking);
        if(bookingDetails.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Your booking is pending", HttpStatus.NOT_FOUND.value()));
        }
        return ResponseEntity.ok().body(bookingDetails);
    }
}
