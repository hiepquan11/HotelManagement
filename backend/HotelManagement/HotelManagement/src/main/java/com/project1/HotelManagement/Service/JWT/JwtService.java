package com.project1.HotelManagement.Service.JWT;

import com.project1.HotelManagement.Entity.UserAccount;
import com.project1.HotelManagement.Service.UserAccount.UserAccountService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
    public static final String SECRET_KEY = "MTIzNDU2NDU5OThEMzIxM0F6eGMzNTE2NTQzMjEzMjE2NTQ5OHEzMTNhMnMxZDMyMnp4M2MyMQ==";

    @Autowired
    private UserAccountService userAccountService;

    //generate token
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        UserAccount userAccount = userAccountService.findByUsername(username);
        if(userAccount != null && userAccount.getRole() != null) {
            if(userAccount.getRole().getRoleName().equals("ADMIN")) {
                claims.put("role", "ADMIN");
                claims.put("username", username);
                claims.put("id", userAccount.getStaff().getStaffId());
                claims.put("name", userAccount.getStaff().getStaffName());
                claims.put("phoneNumber", userAccount.getStaff().getPhoneNumber());
                claims.put("email", userAccount.getStaff().getEmail());
                claims.put("identificationNumber", userAccount.getStaff().getIdentificationNumber());
                claims.put("salary", userAccount.getStaff().getSalary());
                claims.put("birthday", userAccount.getStaff().getBirthDay());
            }
            if(userAccount.getRole().getRoleName().equals("CUSTOMER")) {
                claims.put("role", "CUSTOMER");
                claims.put("id", userAccount.getCustomer().getCustomerId());
                claims.put("name", userAccount.getCustomer().getCustomerName());
                claims.put("enable", userAccount.getEnabled());
                claims.put("email", userAccount.getCustomer().getEmail());
                claims.put("address", userAccount.getCustomer().getAddress());
                claims.put("phoneNumber", userAccount.getCustomer().getPhoneNumber());
                claims.put("identificationNumber", userAccount.getCustomer().getIdentificationNumber());
            }
        }
        return createToken(claims, username);
    }

    // create jwt
    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 60*60*1000))
                .signWith(SignatureAlgorithm.HS256,getSignKey())
                .compact();
    }

    // get secret key
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // get info token
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(getSignKey()).parseClaimsJws(token).getBody();
    }

    // method generic
    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    // check expire time jwt
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // check username jwt
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    // get user id
    public Integer extractUserId(String token){
       return extractClaim(token, claims -> claims.get("id", Integer.class));
    }

    // get user role
    public String extractUserRole(String token){
        return extractClaim(token, claims -> claims.get("role", String.class));
    }

    // get customer email
    public String extractCustomerEmail(String token){
        return extractClaim(token, claims -> claims.get("email", String.class));
    }

    // get customer address
    public String extractCustomerAddress(String token){
        return extractClaim(token, claims -> claims.get("address", String.class));
    }

    // get customer phone number
    public String extractPhoneNumber(String token){
        return extractClaim(token, claims -> claims.get("phoneNumber", String.class));
    }

    // get customer identification number
    public String extractIdentificationNumber(String token){
        return extractClaim(token, claims -> claims.get("identificationNumber", String.class));
    }

    // check token is expire
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // check token is valid
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
