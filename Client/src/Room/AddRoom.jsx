import React, { useState } from "react";

const AddRoom = () => {
  const [roomTypeName, setRoomTypeName] = useState("");
  const [price, setPrice] = useState("");
  const [files, setFiles] = useState(null);
  const [errorMessage, setErrorMessage] = useState("");
  const [successMessage, setSuccessMessage] = useState("");

  // File change handler
  const handleFileChange = (event) => {
    setFiles(event.target.files);
  };

  // Handle form submission
  const handleSubmit = async (event) => {
    event.preventDefault();
    
    if (!roomTypeName || price <= 0) {
      setErrorMessage("Please provide valid room type name and price.");
      return;
    }
    
    const formData = new FormData();
    formData.append("roomTypeName", roomTypeName);
    formData.append("price", price);

    // Append files to FormData
    for (let i = 0; i < files.length; i++) {
      formData.append("files", files[i]);
    }

    try {
      const response = await fetch("http://localhost:8080/api/roomType/add", {
        method: "POST",
        headers: {
          Authorization: "Basic " + btoa("tuantuan:tuantuan"), // Adjust authorization if needed
        },
        body: formData,
      });

      if (!response.ok) {
        const errorText = await response.text();
        setErrorMessage("Error: " + errorText);
        return;
      }

      const data = await response.json();
      console.log(data)
      setSuccessMessage("Room type added successfully!");
      setRoomTypeName("");
      setPrice("");
      setFiles(null);
    } catch (error) {
      setErrorMessage("An error occurred. Please try again later.");
    }
  };

  return (
    <div className="max-w-lg mx-auto p-6 bg-white shadow-lg rounded-lg">
      <h2 className="text-2xl font-bold text-center text-gray-800 mb-6">Add Room Type</h2>

      {errorMessage && (
        <div className="bg-red-500 text-white p-3 rounded mb-4">
          <p>{errorMessage}</p>
        </div>
      )}

      {successMessage && (
        <div className="bg-green-500 text-white p-3 rounded mb-4">
          <p>{successMessage}</p>
        </div>
      )}

      <form onSubmit={handleSubmit}>
        <div className="mb-4">
          <label htmlFor="roomTypeName" className="block text-gray-700 font-semibold mb-2">
            Room Type Name
          </label>
          <input
            type="text"
            id="roomTypeName"
            value={roomTypeName}
            onChange={(e) => setRoomTypeName(e.target.value)}
            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500"
            required
          />
        </div>

        <div className="mb-4">
          <label htmlFor="price" className="block text-gray-700 font-semibold mb-2">
            Price
          </label>
          <input
            type="number"
            id="price"
            value={price}
            onChange={(e) => setPrice(e.target.value)}
            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500"
            required
          />
        </div>

        <div className="mb-4">
          <label htmlFor="files" className="block text-gray-700 font-semibold mb-2">
            Upload Images
          </label>
          <input
            type="file"
            id="files"
            multiple
            onChange={handleFileChange}
            className="w-full px-4 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-indigo-500"
            required
          />
        </div>

        <div className="flex justify-center">
          <button
            type="submit"
            className="bg-indigo-600 text-white px-6 py-2 rounded-md hover:bg-indigo-700 focus:outline-none"
          >
            Add Room Type
          </button>
        </div>
      </form>
    </div>
  );
};

export default AddRoom;
