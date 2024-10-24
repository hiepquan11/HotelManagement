package com.project1.HotelManagement.Repository;

import com.project1.HotelManagement.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "room")
public interface RoomRepository extends JpaRepository<Room, Integer> {
    public Room findByRoomId(int roomId);
}
