package com.project1.HotelManagement.Service.RoomType;

import com.project1.HotelManagement.Entity.RoomType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface RoomTypeService {
    public ResponseEntity<?> saveRoomType(RoomType roomType, MultipartFile[] files);
    public ResponseEntity<?> updateRoomType(int roomTypeId ,RoomType roomType, MultipartFile[] files);
    public ResponseEntity<?> deleteRoomType(RoomType roomType);
}
