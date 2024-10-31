package com.project1.HotelManagement.Service.RoomType;

import com.cloudinary.Cloudinary;
import com.project1.HotelManagement.Entity.Image;
import com.project1.HotelManagement.Entity.RoomType;
import com.project1.HotelManagement.Repository.ImageRepository;
import com.project1.HotelManagement.Repository.RoomRepository;
import com.project1.HotelManagement.Repository.RoomTypeRepository;
import com.project1.HotelManagement.Service.Image.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class RoomTypeIpml implements RoomTypeService{


    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageService imageService;

    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private RoomRepository roomRepository;

    @Override
    public ResponseEntity<?> saveRoomType(RoomType roomType, MultipartFile[] files) {
        List<String> imageUrls = new ArrayList<>();
        try {
            if(roomType.getRoomTypeName() == null){
                return ResponseEntity.badRequest().body("Room type name is not null");
            }
            if(roomType.getPrice() == 0){
                return ResponseEntity.badRequest().body("Room type price is not 0 or null");
            }
            for(MultipartFile file : files){
                String uniqueId = UUID.randomUUID().toString();
                String publicId = roomType.getRoomTypeName() + "_" + uniqueId;
                String url = cloudinary.uploader().upload(file.getBytes(), Map.of("public_id", publicId))
                        .get("url").toString();
                imageUrls.add(url);
            }
            List<Image> images = new ArrayList<>();
            for(String imageUrl : imageUrls){
                Image image = new Image();
                image.setName(roomType.getRoomTypeName());
                image.setImageUrl(imageUrl);
                image.setRoomType(roomType);
                images.add(image);
                imageRepository.save(image);
            }
            roomType.setImage(images);
            RoomType newRoomType = roomTypeRepository.save(roomType);
            return ResponseEntity.status(HttpStatus.CREATED).body(newRoomType);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while saving the room and images: "+e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> updateRoomType(int roomTypeId, RoomType roomType, MultipartFile[] files) {
        RoomType checkRoomType = roomTypeRepository.findByRoomTypeId(roomTypeId);
        if(checkRoomType == null){
            return ResponseEntity.badRequest().body("Room type is not found");
        }
        try {
            checkRoomType.setRoomTypeName(roomType.getRoomTypeName());
            checkRoomType.setPrice(roomType.getPrice());

            if(files.length > 0 && files != null){
                ResponseEntity<?> response = imageService.uploadImage(files, checkRoomType.getRoomTypeName());
                if(response.getStatusCode() == HttpStatus.OK){
                    List<String> imageUrls = (List<String>) response.getBody();

                    List<Image> images = checkRoomType.getImage();
                    for(int i = 0; i < images.size() && i < imageUrls.size(); i++){
                        images.get(i).setImageUrl(imageUrls.get(i));
                        images.get(i).setName(checkRoomType.getRoomTypeName());
                        images.get(i).setRoomType(checkRoomType);
                    }
                    checkRoomType.setImage(images);
                }
            }
            RoomType updatedRoomType = roomTypeRepository.save(checkRoomType);
            return ResponseEntity.ok(updatedRoomType);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while updating the room type: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteRoomType(int roomTypeId) {
        RoomType checkRoomType = roomTypeRepository.findByRoomTypeId(roomTypeId);
        if(checkRoomType == null){
            return ResponseEntity.badRequest().body("Room type is not found");
        }
        try {
            roomTypeRepository.delete(checkRoomType);
            return ResponseEntity.ok("Deleted room type");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the room type: " + e.getMessage())    ;
        }
    }
}
