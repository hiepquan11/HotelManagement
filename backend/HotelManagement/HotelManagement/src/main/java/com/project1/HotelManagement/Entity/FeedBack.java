package com.project1.HotelManagement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table(name = "feedback")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class FeedBack {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedbackId")
    private int feedbackId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "rating")
    private int rating;

    @Column(name = "feedbackDate",  nullable = false)
    private Date feedbackDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REFRESH})
    @JoinColumn(name = "customerId")
    private Customer customer;
}
