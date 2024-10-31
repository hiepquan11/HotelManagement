package com.project1.HotelManagement.Service.Room;

import com.cloudinary.Cloudinary;
import com.project1.HotelManagement.Entity.Image;
import com.project1.HotelManagement.Entity.Room;
import com.project1.HotelManagement.Repository.ImageRepository;
import com.project1.HotelManagement.Repository.RoomRepository;
import com.project1.HotelManagement.Repository.RoomTypeRepository;
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
public class RoomServiceIpml implements RoomService{

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private Cloudinary cloudinary;

    @Override
    public ResponseEntity<?> saveRoom(Room room, MultipartFile[] files) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateRoom(Room room) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteRoom(Room room) {
        return null;
    }
}
