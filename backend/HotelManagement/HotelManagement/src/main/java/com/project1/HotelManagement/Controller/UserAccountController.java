package com.project1.HotelManagement.Controller;

import com.project1.HotelManagement.Entity.Response;
import com.project1.HotelManagement.Entity.UserAccount;
import com.project1.HotelManagement.Security.JwtResponse;
import com.project1.HotelManagement.Security.LoginRequest;
import com.project1.HotelManagement.Service.JWT.JwtService;
import com.project1.HotelManagement.Service.UserAccount.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/userAccount")
public class UserAccountController {

    @Autowired
    private  UserAccountService userAccountService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    private ResponseEntity<?> register(@Validated @RequestBody UserAccount userAccount){
        try {
            ResponseEntity<?> response = userAccountService.register(userAccount);
            return response;
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            if(authentication.isAuthenticated()){
                final String jwt = jwtService.generateToken(loginRequest.getUsername());
                return ResponseEntity.ok(new JwtResponse(jwt));
            }
        } catch (AuthenticationException e){
            return ResponseEntity.badRequest().body(new Response("Username or password was incorrect", 400));
        }
        return ResponseEntity.badRequest().body("Can not authentication");
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(@RequestParam int page,
                                    @RequestParam int size,
                                    @RequestHeader("Authorization") String token){
        return userAccountService.getAll(page, size);
    }
}
