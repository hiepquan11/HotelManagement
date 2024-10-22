package com.project1.HotelManagement.Service.Image;

import com.cloudinary.Cloudinary;
import com.project1.HotelManagement.Entity.Image;
import com.project1.HotelManagement.Repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ImageServiceIpml implements ImageService {

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private Cloudinary cloudinary;
    @Override
    public ResponseEntity<?> uploadImage(MultipartFile[] files, String name) {
        List<String> urlImages = new ArrayList<>();
        try {
            for (MultipartFile file : files) {
                String uniqueId = UUID.randomUUID().toString();
                String public_id = name + "_" + uniqueId;

                String url = cloudinary.uploader().upload(file.getBytes(), Map.of("public_id",public_id))
                        .get("url").toString();

                urlImages.add(url);
            }

            for (String url : urlImages) {
                Image image = new Image();
                image.setImageUrl(url);
                image.setName(name);
                imageRepository.save(image);
            }
            return ResponseEntity.ok().body(urlImages);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
