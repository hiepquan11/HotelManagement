import React, { useEffect, useState } from "react";
import Banner from "./Banner";

const Home = () => {
  const [featuredRooms, setFeaturedRooms] = useState([]);

  useEffect(() => {
    const fetchFeaturedRooms = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/roomType");
        const data = await response.json();
        // Lấy một vài phòng nổi bật
        setFeaturedRooms(data.slice(0, 3)); // Lấy 3 phòng đầu tiên làm mẫu
      } catch (error) {
        console.error("Error fetching room data:", error);
      }
    };

    fetchFeaturedRooms();
  }, []);

  return (
    <div>
      {/* Banner */}
      <Banner />

      {/* Phần giới thiệu */}
      <div className="text-center py-12 px-4 bg-gray-50">
        <h2 className="text-4xl font-bold text-gray-800 mb-4">Welcome to Our Hotel</h2>
        <p className="text-lg text-gray-600 max-w-3xl mx-auto">
          Discover the ultimate experience of comfort and luxury at our hotel. 
          With beautifully designed rooms and top-notch facilities, we ensure your stay will be unforgettable.
        </p>
      </div>

      {/* Phòng nổi bật */}
      <div className="py-12 px-6 bg-white">
        <h2 className="text-3xl font-bold text-gray-800 text-center mb-8">Featured Rooms</h2>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
          {featuredRooms.map((room) => (
            <div
              key={room.id}
              className="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow"
            >
              <img
                src={room.images[0]}
                alt={room.name}
                className="w-full h-48 object-cover"
              />
              <div className="p-4">
                <h3 className="text-xl font-semibold text-gray-800">{room.name}</h3>
                <p className="text-gray-600 mt-2">{room.price}</p>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* Tiện ích khách sạn */}
      <div className="py-12 px-6 bg-gray-50">
        <h2 className="text-3xl font-bold text-gray-800 text-center mb-8">Our Facilities</h2>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-4 gap-8">
          <div className="flex flex-col items-center text-center">
            <img
              src="https://via.placeholder.com/100"
              alt="Swimming Pool"
              className="w-24 h-24 object-contain mb-4"
            />
            <h4 className="text-lg font-semibold text-gray-800">Swimming Pool</h4>
          </div>
          <div className="flex flex-col items-center text-center">
            <img
              src="https://via.placeholder.com/100"
              alt="Spa"
              className="w-24 h-24 object-contain mb-4"
            />
            <h4 className="text-lg font-semibold text-gray-800">Spa & Wellness</h4>
          </div>
          <div className="flex flex-col items-center text-center">
            <img
              src="https://via.placeholder.com/100"
              alt="Gym"
              className="w-24 h-24 object-contain mb-4"
            />
            <h4 className="text-lg font-semibold text-gray-800">Fitness Center</h4>
          </div>
          <div className="flex flex-col items-center text-center">
            <img
              src="https://via.placeholder.com/100"
              alt="Restaurant"
              className="w-24 h-24 object-contain mb-4"
            />
            <h4 className="text-lg font-semibold text-gray-800">Restaurant</h4>
          </div>
        </div>
      </div>

      {/* Đánh giá từ khách hàng */}
      <div className="py-12 px-6 bg-white">
        <h2 className="text-3xl font-bold text-gray-800 text-center mb-8">What Our Guests Say</h2>
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-8">
          <div className="bg-gray-100 rounded-lg p-6 shadow-md">
            <p className="text-gray-600 italic">
              "An amazing experience! The rooms were clean and the staff was very friendly. Highly recommend!"
            </p>
            <p className="text-gray-800 font-bold mt-4">- John Doe</p>
          </div>
          <div className="bg-gray-100 rounded-lg p-6 shadow-md">
            <p className="text-gray-600 italic">
              "Beautiful hotel with great amenities. The spa was fantastic, and the food was excellent."
            </p>
            <p className="text-gray-800 font-bold mt-4">- Jane Smith</p>
          </div>
          <div className="bg-gray-100 rounded-lg p-6 shadow-md">
            <p className="text-gray-600 italic">
              "Loved every moment of my stay! The pool and fitness center were top-notch."
            </p>
            <p className="text-gray-800 font-bold mt-4">- Alex Lee</p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;
