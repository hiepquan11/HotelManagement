package com.project1.HotelManagement.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private String bankCode;
    private String orderInfo;
    private double amount;
    private String responseCode;
    private List<String> roomNumber;
}
