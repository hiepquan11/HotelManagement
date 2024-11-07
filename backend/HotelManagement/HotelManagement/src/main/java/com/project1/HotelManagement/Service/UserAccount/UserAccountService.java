package com.project1.HotelManagement.Service.UserAccount;

import com.project1.HotelManagement.Entity.UserAccount;
import com.project1.HotelManagement.Security.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserAccountService extends UserDetailsService {
    public ResponseEntity<?> register(UserAccount userAccount);
    public UserAccount findByUsername(String username);
}
