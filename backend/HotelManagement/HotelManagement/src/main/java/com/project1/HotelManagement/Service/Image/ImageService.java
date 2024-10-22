package com.project1.HotelManagement.Service.Image;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    public ResponseEntity<?> uploadImage(MultipartFile[] files, String name);
}
