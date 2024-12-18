import React, { useEffect, useState } from "react";

const RoomTypeView = () => {
  const [roomTypes, setRoomTypes] = useState([]);

  // Fetch data từ API
  useEffect(() => {
    fetch("http://localhost:8080/api/roomType")
      .then((response) => response.json())
      .then((data) => setRoomTypes(data))
      .catch((error) => console.error("Error fetching room types:", error));
  }, []);

  // Hàm xoá roomType
  const handleDelete = (id) => {
    fetch(`http://localhost:8080/api/roomType/delete/${id}`, {
      method: "DELETE",
      headers: {
        Authorization: `Bearer ${localStorage.getItem('jwt')}`
      },
    })
      .then(() => {
        alert("Xoá thành công!");
        setRoomTypes((prev) => prev.filter((room) => room.id !== id));
      })
      .catch((error) => console.error("Error deleting room type:", error));
  };

  return (
    <div className="container mx-auto px-4 py-8">
      <h1 className="text-3xl font-bold mb-6">Danh sách Room Types</h1>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {roomTypes.map((room) => (
          <div
            key={room.id}
            className="bg-white shadow-lg rounded-lg overflow-hidden"
          >
            {/* Hiển thị hình ảnh */}
            <div className="w-full h-48 overflow-hidden">
              <img
                src={room.images[0]}
                alt={room.name}
                className="w-full h-full object-cover"
              />
            </div>
            {/* Thông tin phòng */}
            <div className="p-4">
              <h2 className="text-xl font-semibold mb-2">{room.name}</h2>
              <p className="text-gray-600 mb-2">{room.description}</p>
              <p className="text-green-600 font-bold mb-2">
                Giá: {room.price.toLocaleString()} VNĐ
              </p>
              {/* Nút xoá */}
              <button
                onClick={() => handleDelete(room.id)}
                className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600"
              >
                Xoá
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default RoomTypeView;
