package com.project1.HotelManagement.Controller;

import com.project1.HotelManagement.Entity.RoomType;
import com.project1.HotelManagement.Service.RoomType.RoomTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class RoomTypeController {

    @Autowired
    private RoomTypeService roomTypeService;

    @PostMapping(value = "/roomType/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> saveRoomType(@RequestPart("roomType") RoomType roomType,
                                          @RequestPart("images") MultipartFile[] files) {
        return roomTypeService.saveRoomType(roomType, files);
    }
}
