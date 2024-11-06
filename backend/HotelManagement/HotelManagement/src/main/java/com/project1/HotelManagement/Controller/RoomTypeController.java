package com.project1.HotelManagement.Controller;

import com.project1.HotelManagement.Entity.RoomType;
import com.project1.HotelManagement.Service.RoomType.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class RoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;

    @PostMapping(value = "/roomType/add")
    public ResponseEntity<?> saveRoomType(@RequestPart("roomType") RoomType roomType,
                                          @RequestPart("files") MultipartFile[] files) {
        return roomTypeService.saveRoomType(roomType, files);
    }

    @PutMapping("/roomType/update/{roomTypeId}")
    public ResponseEntity<?> updateRoomType(@PathVariable int roomTypeId,
                                            @RequestPart RoomType roomType,
                                            @RequestPart(required = false) MultipartFile[] files) {
        return roomTypeService.updateRoomType(roomTypeId, roomType, files);
    }

    @DeleteMapping("/roomType/delete/{roomTypeId}")
    public ResponseEntity<?> deleteRoomType(@PathVariable int roomTypeId) {
        return roomTypeService.deleteRoomType(roomTypeId);
    }
}
