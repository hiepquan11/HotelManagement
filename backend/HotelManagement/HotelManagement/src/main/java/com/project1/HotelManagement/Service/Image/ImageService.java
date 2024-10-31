package com.project1.HotelManagement.Service.Image;

import com.project1.HotelManagement.Entity.Image;
import com.project1.HotelManagement.Entity.RoomType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    public ResponseEntity<?> uploadImage(MultipartFile[] files, String name);
    List<Image> findImagesByRoomType(RoomType roomType);
}
