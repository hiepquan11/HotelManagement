package com.project1.HotelManagement.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountInfo {
    private int id;
    private String userName;
    private String roleName;
}
