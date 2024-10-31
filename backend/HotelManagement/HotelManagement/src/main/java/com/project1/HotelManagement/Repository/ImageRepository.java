package com.project1.HotelManagement.Repository;

import com.project1.HotelManagement.Entity.Image;
import com.project1.HotelManagement.Entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "image")
public interface ImageRepository extends JpaRepository<Image, Integer> {
    List<Image> findImagesByRoomType(RoomType roomType);
}
