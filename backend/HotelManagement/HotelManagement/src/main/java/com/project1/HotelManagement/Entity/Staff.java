package com.project1.HotelManagement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staffId")
    private int staffId;

    @Column(name = "staffName", nullable = false)
    private String staffName;

    @Column(name = "phoneNumber", nullable = false, length = 10)
    private String phoneNumber;

    @Column(name = "gender")
    private String gender;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "identificationNumber", nullable = false)
    private String identificationNumber;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "birthDay", nullable = false)
    private Date birthDay;

    @Column(name = "salary", nullable = false)
    private double salary;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "roleId")
    private Role role;

    @OneToMany(mappedBy = "staff", fetch = FetchType.LAZY, cascade = {
            CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH
    })
    private List<Booking> bookingList;
}
