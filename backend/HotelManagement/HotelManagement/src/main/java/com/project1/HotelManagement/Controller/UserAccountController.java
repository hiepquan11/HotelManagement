package com.project1.HotelManagement.Controller;

import com.project1.HotelManagement.Entity.UserAccount;
import com.project1.HotelManagement.Security.LoginRequest;
import com.project1.HotelManagement.Service.UserAccount.UserAccountService;
import com.project1.HotelManagement.Service.UserAccount.UserAccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/userAccount")
public class UserAccountController {

    @Autowired
    private  UserAccountService userAccountService;
    @Autowired
    private UserAccountServiceImpl userAccountServiceImpl;
    @Autowired
    private AuthenticationManager authenticationManager;

    @PostMapping("/register")
    private ResponseEntity<?> register(@Validated @RequestBody UserAccount userAccount){
        try {
            ResponseEntity<?> response = userAccountService.register(userAccount);
            return response;
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
