package com.project1.HotelManagement.Service.RoomType;

import com.cloudinary.Cloudinary;
import com.project1.HotelManagement.Entity.Image;
import com.project1.HotelManagement.Entity.Response;
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

import java.util.*;

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
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Room type name is not null", HttpStatus.BAD_REQUEST.value()));
            }
            if(roomType.getPrice() == 0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Room type price is not 0 or null", HttpStatus.BAD_REQUEST.value()));
            }
            if(roomType.getDescription().isEmpty()){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Room type description is not empty", HttpStatus.BAD_REQUEST.value()));
            }
            if(roomType.getCapacity() == 0){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("Room type capacity is not 0", HttpStatus.BAD_REQUEST.value()));
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Room type not found", HttpStatus.NOT_FOUND.value()));
        }
        try {
            checkRoomType.setRoomTypeName(roomType.getRoomTypeName());
            checkRoomType.setPrice(roomType.getPrice());
            checkRoomType.setDescription(roomType.getDescription());

            if(files != null && files.length >0){
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
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Room type not found", HttpStatus.NOT_FOUND.value()));
        }
        try {
            roomTypeRepository.delete(checkRoomType);
            return ResponseEntity.ok().body(new Response("Room type id: "+roomTypeId+" deleted", 200));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the room type: " + e.getMessage())    ;
        }
    }

    @Override
    public ResponseEntity<?> getRoomType() {
        List<RoomType> listRoomTypes = roomTypeRepository.findAllWithImages();
        if(listRoomTypes.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Room type not found", HttpStatus.NOT_FOUND.value()));
        }
       List<Map<String, Object>> response = listRoomTypes.stream().map(roomType ->{
           Map<String, Object> roomTypeData = new HashMap<>();
           roomTypeData.put("id", roomType.getRoomTypeId());
           roomTypeData.put("name", roomType.getRoomTypeName());
           roomTypeData.put("description", roomType.getDescription());
           roomTypeData.put("price", roomType.getPrice());
           roomTypeData.put("images", roomType.getImage().stream().map(Image::getImageUrl).toList());
           return roomTypeData;
       }).toList();

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> getRoomTypeById(int roomTypeId) {
        RoomType checkRoomType = roomTypeRepository.findByRoomTypeId(roomTypeId);
        if(checkRoomType == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Response("Room type not found", HttpStatus.NOT_FOUND.value()));
        }
        return ResponseEntity.ok().body(checkRoomType);
    }
}
