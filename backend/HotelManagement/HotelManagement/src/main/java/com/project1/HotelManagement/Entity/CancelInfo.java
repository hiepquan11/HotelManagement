package com.project1.HotelManagement.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CancelInfo {
    private int bookingId;
    private String customerName;
    private Date cancelDate;
    private Date bookingDate;
    private String status;
    private double cancelFee;
}
