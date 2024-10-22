package com.project1.HotelManagement.Service.RoomType;

import com.project1.HotelManagement.Entity.RoomType;
import org.springframework.http.ResponseEntity;

public interface RoomTypeService {
    public ResponseEntity<?> saveRoomType(RoomType roomType);
    public ResponseEntity<?> updateRoomType(RoomType roomType);
    public ResponseEntity<?> deleteRoomType(RoomType roomType);
}
