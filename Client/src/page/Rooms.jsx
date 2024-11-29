import React, { useEffect, useState } from 'react';

// const roomsData = [
//   { id: 1, name: 'Deluxe Room', price: '$150', image: 'https://via.placeholder.com/150' },
//   { id: 2, name: 'Suite Room', price: '$250', image: 'https://via.placeholder.com/150' },
//   { id: 3, name: 'Standard Room', price: '$100', image: 'https://via.placeholder.com/150' },
//   { id: 4, name: 'Family Room', price: '$200', image: 'https://via.placeholder.com/150' },
// ];


const Rooms = () => {
  const [roomData, setRoomData] = useState([]);

  useEffect(() => {
    const fetchRoomTypes = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/roomType');
        const data = await response.json();
        setRoomData(data)
        console.log("this is room data")
        console.log(roomData)
      } catch (error) {
        console.log(error)
      } finally{

      }
    };
    fetchRoomTypes();
  },[])

  return (
    <div className="flex flex-col items-center justify-center min-h-screen bg-gray-100">
      <h1 className="text-4xl font-bold text-gray-800">Rooms</h1>
      <p className="mt-4 text-gray-600">Browse our luxurious and comfortable rooms.</p>
      
      {/* Grid container for rooms */}
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-6 mt-8">
        {roomData.map((room) => (
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
  );
};

export default Rooms;
