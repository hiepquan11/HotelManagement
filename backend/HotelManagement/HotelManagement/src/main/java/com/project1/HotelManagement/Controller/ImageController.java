package com.project1.HotelManagement.Controller;

import com.project1.HotelManagement.Entity.Image;
import com.project1.HotelManagement.Entity.RoomType;
import com.project1.HotelManagement.Repository.ImageRepository;
import com.project1.HotelManagement.Repository.RoomTypeRepository;
import com.project1.HotelManagement.Service.Image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @Autowired
    private RoomTypeRepository roomTypeRepository;


    @GetMapping("/image/{roomTypeId}")
    public ResponseEntity<?> getImageByRoomType(@PathVariable("roomTypeId") int roomTypeId) {
        RoomType checkRoomType = roomTypeRepository.findByRoomTypeId(roomTypeId);
        if (checkRoomType == null) {
            return ResponseEntity.notFound().build();
        }
        List<Image> images = imageService.findImagesByRoomType(checkRoomType);
        return ResponseEntity.ok(images);
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile[] file, @RequestParam("name") String name) {
        ResponseEntity<?> response = imageService.uploadImage(file, name);
        if(response == null){
            return ResponseEntity.badRequest().body("Upload failed");
        }
        return ResponseEntity.ok(response);
    }
}
