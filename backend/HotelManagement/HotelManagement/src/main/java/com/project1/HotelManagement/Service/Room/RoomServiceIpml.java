package com.project1.HotelManagement.Service.Room;

import com.cloudinary.Cloudinary;
import com.project1.HotelManagement.Entity.Response;
import com.project1.HotelManagement.Entity.Room;
import com.project1.HotelManagement.Entity.RoomType;
import com.project1.HotelManagement.Repository.ImageRepository;
import com.project1.HotelManagement.Repository.RoomRepository;
import com.project1.HotelManagement.Repository.RoomTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoomServiceIpml implements RoomService{

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Override
    public ResponseEntity<?> getRoom() {
        List<Room> rooms = roomRepository.findAllWithRoomType();
        if(rooms.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Room not found", HttpStatus.NOT_FOUND.value()));
        }

        List<Map<String, Object>> response = rooms.stream().map(room ->{
            Map<String, Object> roomData = new HashMap<>();
            roomData.put("id", room.getRoomId());
            roomData.put("Quantity of bed", room.getBedQuantity());
            roomData.put("Room number", room.getRoomNumber());
            roomData.put("Room Type", room.getRoomType().getRoomTypeName());
            roomData.put("Status", room.getStatus());
            roomData.put("RoomType ID", room.getRoomType().getRoomTypeId());

            return roomData;
        }).toList();

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> saveRoom(Room room) {
        if(room.getRoomNumber().isEmpty()){
            return ResponseEntity.badRequest().body(new Response("Room must have room number", HttpStatus.BAD_REQUEST.value()));
        }
        if(room.getBedQuantity() == 0){
            return ResponseEntity.badRequest().body(new Response("BedQuantity is not 0", HttpStatus.BAD_REQUEST.value()));
        }
        if(room.getDescription() == null){
            return ResponseEntity.badRequest().body(new Response("Room must have description", HttpStatus.BAD_REQUEST.value()));
        }
        if(room.getRoomType() == null){
            return ResponseEntity.badRequest().body(new Response("Room must have a room type", HttpStatus.BAD_REQUEST.value()));
        }
        RoomType checkRoomType = roomTypeRepository.findByRoomTypeId(room.getRoomType().getRoomTypeId());
        if(checkRoomType == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("RoomType is not exist", HttpStatus.NOT_FOUND.value()));
        }

        room.setRoomType(checkRoomType);
        room.setStatus("AVAILABLE");
        Room newRoom = roomRepository.save(room);
        return ResponseEntity.ok(newRoom);
    }

    @Override
    public ResponseEntity<?> updateRoom(Room room) {
        Room checkRoom = roomRepository.findByRoomId(room.getRoomId());
        if (checkRoom == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Room does not exist.", HttpStatus.NOT_FOUND.value()));
        }

        if (room.getRoomType() == null || room.getRoomType().getRoomTypeId() == 0) {
            return ResponseEntity.badRequest().body(new Response("RoomType must be specified.", HttpStatus.BAD_REQUEST.value()));
        }
        RoomType checkRoomType = roomTypeRepository.findByRoomTypeId(room.getRoomType().getRoomTypeId());
        if(checkRoomType == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("RoomType is not exist", HttpStatus.NOT_FOUND.value()));
        }
        try {
            checkRoom.setBedQuantity(room.getBedQuantity());
            checkRoom.setStatus(room.getStatus());
            checkRoom.setDescription(room.getDescription());
            checkRoom.setRoomType(checkRoomType);

            Room updateRoom = roomRepository.save(checkRoom);
            return ResponseEntity.ok(updateRoom);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteRoom(int  roomId) {
        Room checkRoom = roomRepository.findByRoomId(roomId);
        if(checkRoom == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Room is not exist", HttpStatus.NOT_FOUND.value()));
        }
        try {
            roomRepository.deleteById(roomId);
            return ResponseEntity.ok().body("Room deleted successfully");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
