package com.project1.HotelManagement.Repository;

import com.project1.HotelManagement.Entity.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "roomtype")
public interface RoomTypeRepository extends JpaRepository<RoomType, Integer> {
    public RoomType findByRoomTypeId(int roomTypeId);
    public RoomType deleteByRoomTypeId(int roomTypeId);
}
