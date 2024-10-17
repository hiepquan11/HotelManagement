package com.project1.HotelManagement.Service.UserAccount;

import com.project1.HotelManagement.Entity.UserAccount;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAccountService{
    public ResponseEntity<?> register(UserAccount userAccount);
}
