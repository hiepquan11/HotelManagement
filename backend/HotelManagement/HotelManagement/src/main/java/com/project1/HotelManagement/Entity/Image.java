package com.project1.HotelManagement.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "image")
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imageId")
    private int imageId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "imageUrl", nullable = false)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinColumn(name = "roomTypeId")
    private RoomType roomType;
}
