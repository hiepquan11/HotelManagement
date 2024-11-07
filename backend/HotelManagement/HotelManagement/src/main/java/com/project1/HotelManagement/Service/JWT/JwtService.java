package com.project1.HotelManagement.Service.JWT;

import com.project1.HotelManagement.Entity.UserAccount;
import com.project1.HotelManagement.Service.UserAccount.UserAccountService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
            if(userAccount.getRole().equals("ADMIN")) {
                claims.put("role", "ADMIN");
            }
            if(userAccount.getRole().equals("CUSTOMER")) {
                claims.put("role", "CUSTOMER");
            }
        }
        claims.put("id", userAccount.getUserAccountId());
        claims.put("enable", userAccount.getEnabled());
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