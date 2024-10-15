package com.project1.HotelManagement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Table(name = "roomtype")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roomTypeId")
    private int roomTypeId;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "roomTypeName", nullable = false)
    private String roomTypeName;

    @OneToMany(mappedBy = "roomType",fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE})
    private List<Room> room;

    @OneToMany(mappedBy = "roomType", fetch = FetchType.EAGER, cascade = {
            CascadeType.ALL
    })
    private List<Image> image;
}
