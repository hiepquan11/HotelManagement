package com.project1.HotelManagement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerId")
    private int customerId;

    @Column(name = "customerName" , nullable = false)
    private String customerName;

    @Column(name = "phoneNumber", nullable = false, length = 10)
    private String phoneNumber;

    @Column(name = "gender")
    private String gender;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "countryName", nullable = false)
    private String countryName;

    @Column(name = "identificationNumber", nullable = false)
    private String identificationNumber;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<FeedBack> feedBack;

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Booking> booking;
}
