package com.project1.HotelManagement.Service.Room;

import com.project1.HotelManagement.Entity.Image;
import com.project1.HotelManagement.Entity.Room;
import com.project1.HotelManagement.Repository.ImageRepository;
import com.project1.HotelManagement.Repository.RoomRepository;
import com.project1.HotelManagement.Repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RoomServiceIpml implements RoomService{

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public ResponseEntity<?> saveRoom(Room room) {
        try {
            if(room.getRoomType() == null){
                return ResponseEntity.badRequest().body("Room type is not null");
            }
            if(room.getBedQuantity() == 0){
                return ResponseEntity.badRequest().body("BedQuantity is not 0");
            }
            if(room.getDescription() == null){
                return ResponseEntity.badRequest().body("Description is not null");
            }
            Room newRoom = roomRepository.save(room);
            return ResponseEntity.status(HttpStatus.CREATED).body(newRoom);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateRoom(Room room) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteRoom(Room room) {
        return null;
    }
}
