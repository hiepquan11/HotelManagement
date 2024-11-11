package com.project1.HotelManagement.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest {
    private Date checkInDate;
    private Date checkOutDate;
    private String customerName;
    private String customerNation;
    private String customerEmail;
    private String customerPhoneNumber;
    private String customerIdentification;
    private int quantityRoom;
}
