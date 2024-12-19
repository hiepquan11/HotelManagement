package com.project1.HotelManagement.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Table(name = "booking")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingId")
    private int bookingId;

    @Column(name = "bookingDate", nullable = false)
    private Date bookingDate;

    @Column(name = "bookingStatus")
    private String bookingStatus;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "quantityRoom", nullable = false)
    private int quantityRoom;

    @Column(name = "totalAmount", nullable = false)
    private double totalAmount;

    @Column(name = "checkoutDate", nullable = false)
    private Date checkOutDate;

    @Column(name = "checkinDate", nullable = false)
    private Date checkInDate;

    @Column(name = "roomtype")
    private int roomType;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BookingDetail> bookingDetails;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.DETACH
    })
    @JoinColumn(name = "customerId")
    @JsonIgnore
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REFRESH, CascadeType.DETACH
    })
    @JoinColumn(name = "staffId")
    @JsonIgnore
    private Staff staff;

    @OneToOne(mappedBy = "booking",fetch = FetchType.LAZY, cascade ={ CascadeType.PERSIST, CascadeType.MERGE})
    private Payment payment;

    @Column(name = "cancelFee")
    private double cancelFee;
}
