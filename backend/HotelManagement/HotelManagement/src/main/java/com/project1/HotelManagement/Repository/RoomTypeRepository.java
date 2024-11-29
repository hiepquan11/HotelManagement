package com.project1.HotelManagement.Repository;

import com.project1.HotelManagement.Entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "roomtype")
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {
    RoomType findByRoomTypeId(int roomTypeId);
    RoomType deleteByRoomTypeId(int roomTypeId);

    @Query("SELECT rt FROM RoomType rt JOIN FETCH rt.image")
    List<RoomType> findAllWithImages();
}
