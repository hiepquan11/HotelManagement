import React, { useEffect, useState } from "react";

const Banner = () => {
  const [roomTypes, setRoomTypes] = useState([]);
  const [currentIndex, setCurrentIndex] = useState(0); // Index hiện tại

  // Fetch dữ liệu RoomType
  useEffect(() => {
    const fetchRoomTypes = async () => {
      try {
        const response = await fetch("http://localhost:8080/api/roomType");
        const data = await response.json();
        setRoomTypes(data);
      } catch (error) {
        console.error("Error fetching room types:", error);
      }
    };

    fetchRoomTypes();
  }, []);

  // Auto slide logic
  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentIndex((prevIndex) =>
        prevIndex === roomTypes.length - 1 ? 0 : prevIndex + 1
      );
    }, 3000); // Chuyển slide mỗi 3 giây

    return () => clearInterval(interval); // Dọn dẹp interval khi component bị hủy
  }, [roomTypes]);

  // Hàm điều hướng thủ công
  const handlePrev = () => {
    setCurrentIndex((prevIndex) =>
      prevIndex === 0 ? roomTypes.length - 1 : prevIndex - 1
    );
  };

  const handleNext = () => {
    setCurrentIndex((prevIndex) =>
      prevIndex === roomTypes.length - 1 ? 0 : prevIndex + 1
    );
  };

  return (
    <div className="relative w-full h-[500px] bg-gray-200 overflow-hidden">
      {roomTypes.length > 0 ? (
        <div className="w-full h-full flex">
          {/* Hiển thị hình ảnh */}
          {roomTypes.map((room, index) => (
            <div
              key={room.id}
              className={`absolute w-full h-full transition-opacity duration-1000 ${
                index === currentIndex ? "opacity-100" : "opacity-0"
              }`}
              style={{
                backgroundImage: `url(${room.images[0]})`,
                backgroundSize: "cover",
                backgroundPosition: "center",
              }}
            >
              <div className="bg-black bg-opacity-50 w-full h-full flex flex-col justify-center items-center text-white">
                <h1 className="text-4xl font-bold mb-4">{room.name}</h1>
                <p className="text-lg">{room.description}</p>
              </div>
            </div>
          ))}

          {/* Nút điều hướng */}
          <button
            onClick={handlePrev}
            className="absolute top-1/2 left-4 transform -translate-y-1/2 bg-black bg-opacity-50 text-white rounded-full w-10 h-10 flex items-center justify-center hover:bg-opacity-75 transition duration-300"
          >
            &larr;
          </button>
          <button
            onClick={handleNext}
            className="absolute top-1/2 right-4 transform -translate-y-1/2 bg-black bg-opacity-50 text-white rounded-full w-10 h-10 flex items-center justify-center hover:bg-opacity-75 transition duration-300"
          >
            &rarr;
          </button>
        </div>
      ) : (
        <div className="flex items-center justify-center w-full h-full">
          <p className="text-gray-500">Loading...</p>
        </div>
      )}
    </div>
  );
};

export default Banner;
