package com.project1.HotelManagement.Service.RoomType;

import com.project1.HotelManagement.Entity.RoomType;
import com.project1.HotelManagement.Repository.ImageRepository;
import com.project1.HotelManagement.Repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoomTypeIpml implements RoomTypeService{


    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public ResponseEntity<?> saveRoomType(RoomType roomType) {
        if(roomType.getRoomTypeName() == null){
            return ResponseEntity.badRequest().body("Room type name is not null");
        }
        if(roomType.getPrice() == 0){
            return ResponseEntity.badRequest().body("Room type price is not 0 or null");
        }
        RoomType newRoomType = roomTypeRepository.save(roomType);
        return ResponseEntity.ok(newRoomType);
    }

    @Override
    public ResponseEntity<?> updateRoomType(RoomType roomType) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteRoomType(RoomType roomType) {
        return null;
    }
}
