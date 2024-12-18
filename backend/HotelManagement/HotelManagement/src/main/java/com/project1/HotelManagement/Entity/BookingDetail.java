package com.project1.HotelManagement.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bookingdetail")
public class BookingDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookingDetailId")
    private int bookingDetailId;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "bookingId", nullable = false)
    @JsonBackReference("booking")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "roomId", nullable = false)
    private Room room;
}
