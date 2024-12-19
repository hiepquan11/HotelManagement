import React, { useEffect, useState } from "react";
import axios from "axios";

const AddRoom = () => {
  const [roomTypes, setRoomTypes] = useState([]);
  const [selectedRoomType, setSelectedRoomType] = useState(null);
  const [roomNumber, setRoomNumber] = useState("");
  const [bedQuantity, setBedQuantity] = useState(1);
  const [description, setDescription] = useState("");
  const [response, setResponse] = useState(null);
  const [isLoading, setIsLoading] = useState(false); // Loading state

  useEffect(() => {
    const token = localStorage.getItem("jwt");
    if (!token) {
      alert("You need to log in first!");
      window.location.href = "/login";
    }
  }, []);

  useEffect(() => {
    const token = localStorage.getItem("jwt");
    axios
      .get("http://localhost:8080/api/roomType", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((res) => setRoomTypes(res.data))
      .catch((err) => console.error(err));
  }, []);

  const handleSubmit = (e) => {
    e.preventDefault();

    const payload = {
      roomType: { roomTypeId: selectedRoomType },
      roomNumber,
      bedQuantity,
      description,
    };

    const token = localStorage.getItem("jwt");
    setIsLoading(true); // Bắt đầu loading

    axios
      .post("http://localhost:8080/api/room/addRoom", payload, {
        headers: { Authorization: `Bearer ${token}` },
      })
      .then((res) => {
        setResponse(res.data);
        alert("Room added successfully!");
        // Reset các trường về rỗng
        setSelectedRoomType(null);
        setRoomNumber("");
        setBedQuantity(1);
        setDescription("");
      })
      .catch((err) => {
        console.error(err);
        alert("Failed to add room. Please check your input or token.");
      })
      .finally(() => {
        setIsLoading(false); // Kết thúc loading
      });
  };

  return (
    <div className="container mx-auto p-6">
      <h1 className="text-2xl font-bold mb-4">Add Room</h1>

      <form onSubmit={handleSubmit} className="space-y-4">
        <div>
          <label htmlFor="roomType" className="block font-medium">
            Room Type
          </label>
          <select
            id="roomType"
            className="border border-gray-300 rounded p-2 w-full"
            value={selectedRoomType || ""}
            onChange={(e) => setSelectedRoomType(e.target.value)}
            required
          >
            <option value="" disabled>
              Select Room Type
            </option>
            {roomTypes.map((type) => (
              <option key={type.id} value={type.id}>
                {type.name} - {type.price} VND
              </option>
            ))}
          </select>
        </div>

        <div>
          <label htmlFor="roomNumber" className="block font-medium">
            Room Number
          </label>
          <input
            type="text"
            id="roomNumber"
            className="border border-gray-300 rounded p-2 w-full"
            value={roomNumber}
            onChange={(e) => setRoomNumber(e.target.value)}
            placeholder="Enter Room Number"
            required
          />
        </div>

        <div>
          <label htmlFor="bedQuantity" className="block font-medium">
            Bed Quantity
          </label>
          <input
            type="number"
            id="bedQuantity"
            className="border border-gray-300 rounded p-2 w-full"
            value={bedQuantity}
            onChange={(e) => setBedQuantity(parseInt(e.target.value, 10))}
            min="1"
            required
          />
        </div>

        <div>
          <label htmlFor="description" className="block font-medium">
            Description
          </label>
          <textarea
            id="description"
            className="border border-gray-300 rounded p-2 w-full"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
            placeholder="Enter Description"
            rows="4"
            required
          />
        </div>

        {/* Nút Submit */}
        <button
          type="submit"
          className="bg-blue-500 hover:bg-blue-600 text-white font-bold py-2 px-4 rounded"
          disabled={isLoading} // Disable khi đang loading
        >
          {isLoading ? "Loading..." : "Add Room"}
        </button>
      </form>

      {/* Hiển thị kết quả */}
      {response && (
        <div className="mt-6 bg-green-100 p-4 rounded">
          <h2 className="font-bold text-green-700">Room Added Successfully!</h2>
          <p>Room ID: {response.id}</p>
          <p>Room Number: {response.roomNumber}</p>
          <p>Bed Quantity: {response.bedQuantity}</p>
        </div>
      )}
    </div>
  );
};

export default AddRoom;
