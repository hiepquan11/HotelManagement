package com.project1.HotelManagement.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
    private String bankCode;
    private String orderInfo;
    private double amount;
    private String responseCode;
}
