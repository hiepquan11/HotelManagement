package com.project1.HotelManagement.Controller;

import com.project1.HotelManagement.Entity.Room;
import com.project1.HotelManagement.Service.Room.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping("/addRoom")
    public ResponseEntity<?> addRoom(@Validated @RequestBody Room room) {
//       ResponseEntity<?> response = roomService.saveRoom(room);
//       if(response == null){
//           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save room");
//       }
       return null;
    }
}
