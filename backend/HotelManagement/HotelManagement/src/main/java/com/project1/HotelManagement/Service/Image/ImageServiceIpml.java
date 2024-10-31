package com.project1.HotelManagement.Service.Image;

import com.cloudinary.Cloudinary;
import com.project1.HotelManagement.Entity.Image;
import com.project1.HotelManagement.Repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(ImageServiceIpml.class);

    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private Cloudinary cloudinary;
    @Override
    public ResponseEntity<?> uploadImage(MultipartFile[] files, String name) {
        List<String> urlImages = new ArrayList<>();
        if(files == null || files.length == 0){
            return ResponseEntity.badRequest().body("No files uploaded");
        }
        try {
            for (MultipartFile file : files) {
                if(file.isEmpty()){
                    logger.warn("Skipped empty file");
                    continue;
                }
                String uniqueId = UUID.randomUUID().toString();
                String public_id = name + "_" + uniqueId;

                String url = cloudinary.uploader().upload(file.getBytes(), Map.of("public_id",public_id))
                        .get("url").toString();
                logger.info("Uploaded file to Cloudinary with url: {}", url);

                urlImages.add(url);
            }

            for (String url : urlImages) {
                Image image = new Image();
                image.setImageUrl(url);
                image.setName(name);
                imageRepository.save(image);
                logger.info("Saved image with url {} to db", url);
            }
            return ResponseEntity.ok().body(urlImages);
        } catch (Exception e){
            logger.error("Error uploading image {}", e.getMessage());
            return ResponseEntity.badRequest().body("Failed to upload images");
        }
    }
}
