package com.project1.HotelManagement.Controller;

import com.project1.HotelManagement.Service.Image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/image")
public class ImageController {

    @Autowired
    private ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile[] file, @RequestParam("name") String name) {
        ResponseEntity<?> response = imageService.uploadImage(file, name);
        if(response == null){
            return ResponseEntity.badRequest().body("Upload failed");
        }
        return ResponseEntity.ok(response);
    }
}
