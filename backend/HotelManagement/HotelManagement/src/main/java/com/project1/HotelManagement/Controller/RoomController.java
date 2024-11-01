package com.project1.HotelManagement.Controller;

import com.project1.HotelManagement.Entity.Room;
import com.project1.HotelManagement.Service.Room.RoomService;
import com.project1.HotelManagement.Service.Room.RoomServiceIpml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RoomController {

    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomServiceIpml roomServiceIpml;

    @PostMapping("/room/addRoom")
    public ResponseEntity<?> addRoom(@Validated @RequestBody Room room) {
        ResponseEntity<?> response = roomService.saveRoom(room);
        if (response.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.badRequest().body(response);
        }
        return response;
    }

    @PutMapping("/room/updateRoom")
    public ResponseEntity<?> updateRoom(@RequestBody Room room) {
        ResponseEntity<?> response = roomService.updateRoom(room);
        if (response.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.badRequest().body(response);
        }
        return response;
    }

    @DeleteMapping("/room/deleteRoom/{roomId}")
    public ResponseEntity<?> deleteRoom(@PathVariable int roomId){
        ResponseEntity<?> response = roomService.deleteRoom(roomId);
        if (response.getStatusCode() != HttpStatus.OK) {
            return ResponseEntity.badRequest().body(response);
        }
        return response;
    }
}
