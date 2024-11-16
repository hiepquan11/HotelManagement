
import React, { useState } from "react";
import axios from "axios";

const SignUp = () => {
  const [formData, setFormData] = useState({
    customerName: "",
    phoneNumber: "",
    gender: "",
    address: "",
    countryName: "",
    identificationNumber: "",
    email: "",
    userName: "",
    password: "",
    enabled: false,
  });
  const countries = [
    "Việt Nam",
    "United States",
    "Canada",
    "Australia",
    "Japan",
    "India",
    "France",
    "Germany",
  ];

  const [message, setMessage] = useState("");

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData({
      ...formData,
      [name]: type === "checkbox" ? checked : value,
    });
  };
  const handleSubmit = async (e) => {
    e.preventDefault();
  
    const dataToSend = {
      customer: {
        customerName: formData.customerName,
        phoneNumber: formData.phoneNumber,
        gender: formData.gender,
        address: formData.address,
        countryName: formData.countryName,
        identificationNumber: formData.identificationNumber,
        email: formData.email,
      },
      userName: formData.userName,
      password: formData.password,
      enabled: formData.enabled,
    };
  
    try {
      const response = await axios.post(
        "http://localhost:8080/api/userAccount/register",
        dataToSend,
        {
          headers: { "Content-Type": "application/json" },
        }
      );
  
      if (response.status === 200) {
        setMessage("Registration successful!");
      } else {
        setMessage("Registration failed. Please try again.");
      }
    } catch (error) {
      // Check for backend error response and set message accordingly
      if (error.response && error.response.data) {
        setMessage(error.response.data); // Display backend message (e.g., "Customer Name is Empty")
      } else {
        setMessage("Registration failed. Please try again.");
      }
    }
  };
  
  

  return (
    <div className="flex items-center justify-center min-h-screen bg-gray-100">
      <form onSubmit={handleSubmit} className="bg-white p-8 rounded-lg shadow-md w-full max-w-md">
        <h2 className="text-2xl font-bold mb-6 text-center">Register</h2>

        <div className="mb-4">
          <label className="block text-sm font-medium text-gray-700">Customer Name</label>
          <input
            type="text"
            name="customerName"
            value={formData.customerName}
            onChange={handleChange}
            className="mt-1 p-2 block w-full border border-gray-300 rounded-md"
          />
        </div>

        <div className="mb-4">
          <label className="block text-sm font-medium text-gray-700">Phone Number</label>
          <input
            type="text"
            name="phoneNumber"
            value={formData.phoneNumber}
            onChange={handleChange}
            className="mt-1 p-2 block w-full border border-gray-300 rounded-md"
          />
        </div>

          <div className="mb-4">
        <label className="block text-sm font-medium text-gray-700">Giới tính</label>
        <select
          name="gender"
          value={formData.gender}
          onChange={handleChange}
          className="mt-1 p-2 block w-full border border-gray-300 rounded-md"
        >
          <option value="Nam">Nam</option>
          <option value="Nữ">Nữ</option>
        </select>
      </div>


        <div className="mb-4">
          <label className="block text-sm font-medium text-gray-700">Address</label>
          <input
            type="text"
            name="address"
            value={formData.address}
            onChange={handleChange}
            className="mt-1 p-2 block w-full border border-gray-300 rounded-md"
          />
        </div>

        <div className="mb-4">
        <label className="block text-sm font-medium text-gray-700">Country Name</label>
        <select
          name="countryName"
          value={formData.countryName}
          onChange={handleChange}
          className="mt-1 p-2 block w-full border border-gray-300 rounded-md"
        >
          {countries.map((country) => (
            <option key={country} value={country}>
              {country}
            </option>
          ))}
        </select>
        <p className="mt-2">Selected Country: {formData.countryName}</p>
      </div>

        <div className="mb-4">
          <label className="block text-sm font-medium text-gray-700">Identification Number</label>
          <input
            type="text"
            name="identificationNumber"
            value={formData.identificationNumber}
            onChange={handleChange}
            className="mt-1 p-2 block w-full border border-gray-300 rounded-md"
          />
        </div>

        <div className="mb-4">
          <label className="block text-sm font-medium text-gray-700">Email</label>
          <input
            type="email"
            name="email"
            value={formData.email}
            onChange={handleChange}
            className="mt-1 p-2 block w-full border border-gray-300 rounded-md"
          />
        </div>

        <div className="mb-4">
          <label className="block text-sm font-medium text-gray-700">Username</label>
          <input
            type="text"
            name="userName"
            value={formData.userName}
            onChange={handleChange}
            className="mt-1 p-2 block w-full border border-gray-300 rounded-md"
          />
        </div>

        <div className="mb-4">
          <label className="block text-sm font-medium text-gray-700">Password</label>
          <input
            type="password"
            name="password"
            value={formData.password}
            onChange={handleChange}
            className="mt-1 p-2 block w-full border border-gray-300 rounded-md"
          />
        </div>

        <div className="mb-4 flex items-center">
          <input
            type="checkbox"
            name="enabled"
            checked={formData.enabled}
            onChange={handleChange}
            className="mr-2"
          />
          <label className="text-sm font-medium text-gray-700">Enabled</label>
        </div>

        <button
          type="submit"
          className="w-full bg-blue-500 text-white p-2 rounded-md hover:bg-blue-600 transition"
        >
          Register
        </button>

        {message && <p className="mt-4 text-center text-red-500">{message}</p>}
      </form>
    </div>
  );
};

export default SignUp;
