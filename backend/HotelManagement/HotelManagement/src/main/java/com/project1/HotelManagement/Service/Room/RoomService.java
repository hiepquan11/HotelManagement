package com.project1.HotelManagement.Service.Room;

import com.project1.HotelManagement.Entity.Room;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface RoomService {
    public ResponseEntity<?> saveRoom(Room room);
    public ResponseEntity<?> updateRoom(Room room);
    public ResponseEntity<?> deleteRoom(Room room);
}
