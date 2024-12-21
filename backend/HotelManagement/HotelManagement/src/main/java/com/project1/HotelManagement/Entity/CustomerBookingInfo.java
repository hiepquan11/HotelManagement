package com.project1.HotelManagement.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBookingInfo {
    private String customerName;
    private Date bookingDate;
    private String paymentInfo;
    private String customerPhoneNumber;
    private double paymentAmount;
}
