import React, { useEffect, useState } from "react";
import { useParams, useLocation } from "react-router-dom";
import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation, Pagination, Autoplay, EffectCoverflow } from "swiper/modules";
import "swiper/swiper-bundle.css";

const RoomDetails = () => {
  const { id } = useParams();
  const location = useLocation();
  const [room, setRoom] = useState(location.state?.room || null);

  useEffect(() => {
    if (!room) {
      const fetchRooms = async () => {
        try {
          const response = await fetch("http://localhost:8080/api/roomType");
          const data = await response.json();
          const foundRoom = data.find((item) => item.id === parseInt(id));
          setRoom(foundRoom || null);
        } catch (error) {
          console.error("Error fetching room data:", error);
        }
      };

      fetchRooms();
    }
  }, [id, room]);

  if (!room) {
    return <div>Loading room details...</div>;
  }

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
      <div className="bg-white rounded-lg shadow-lg p-8 max-w-2xl">
        {/* Swiper Slider */}
        <Swiper
          modules={[Navigation, Pagination, Autoplay, EffectCoverflow]}
          effect="coverflow"
          grabCursor={true}
          centeredSlides={true}
          slidesPerView="auto"
          loop={true}
          autoplay={{
            delay: 3000,
            disableOnInteraction: false,
          }}
          pagination={{ clickable: true }}
          navigation={true}
          coverflowEffect={{
            rotate: 30,
            stretch: 0,
            depth: 100,
            modifier: 1,
            slideShadows: true,
          }}
          className="w-full"
        >
          {room.images.map((image, index) => (
          <SwiperSlide key={index} className="w-full flex justify-center items-center">
          <img
            src={image}
            alt={`${room.name} - ${index + 1}`}
            className="w-full h-[500px] object-cover rounded-lg" // Chiều cao tùy chỉnh
          />
        </SwiperSlide>
 
          ))}
        </Swiper>

        {/* Room Details */}
        <h1 className="text-3xl font-bold text-gray-800 mt-4">Loại phòng:{room.name}</h1>
        <p className="text-gray-600 mt-2">Mô tả: {room.description}</p>
        <p className="text-gray-800 mt-4 font-semibold">Giá: {room.price}/Ngày</p>
      </div>
    </div>
  );
};

export default RoomDetails;
