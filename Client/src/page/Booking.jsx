import React, { useState, useEffect, useContext } from 'react';
import { AuthContext } from "./AuthContext";
import { useNavigate } from 'react-router-dom';


const Booking = () => {
  const [formData, setFormData] = useState({
    checkInDate: '',
    checkOutDate: '',
    quantityRoom: 1,
    quantityCustomer: 1,
    roomType: '',
    customerEmail: '',
    customerPhoneNumber: '',
    customerName: '',
    customerIdentification: ''
  });

const { isAuthenticated, user } = useContext(AuthContext); // Use AuthContext

  const [responseMessage, setResponseMessage] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [roomTypes, setRoomTypes] = useState([]);
  const navigate = useNavigate();
  
  useEffect(() => {
    const fetchRoomTypes = async () => {
      try {
        const response = await fetch('http://localhost:8080/roomtype');
        const data = await response.json();
        const fetchedRoomTypes = data._embedded?.roomTypes || [];
        setRoomTypes(fetchedRoomTypes);
      } catch (error) {
        console.error('Error fetching room types:', error);
      }
    };
  
    fetchRoomTypes();
  }, []);
  console.log(roomTypes)

useEffect(() =>{
  if(isAuthenticated && user){
    setFormData((prevData) =>({
      ...prevData,
      customerEmail: user.email || '',
      customerName: user.name || '',
      customerPhoneNumber: user.phoneNumber || '',
      customerIdentification: user.identificationNumber || ''
    }))
  }
},[isAuthenticated, user])

  // Handle form field changes
  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value
    });
  };

  // Handle form submission
  const handleSubmit = async (e) => {
    e.preventDefault();
    setIsLoading(true);
    setResponseMessage(''); // Reset message trước khi gửi yêu cầu
  
    try {
      const response = await fetch('http://localhost:8080/api/booking/createBooking', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(formData)
      });
  
      const result = await response.json();
  
      if (response.ok) {
        setResponseMessage(result.body.message); // Thông báo thành công
        navigate('/YourBooking')
      } else {
        // Hiển thị lỗi từ API nếu có
        const errorMessage = result.body?.message || 'Error creating booking.';
        setResponseMessage(errorMessage);
      }
    } catch (error) {
      setResponseMessage('An error occurred while creating the booking.');
    } finally {
      setIsLoading(false);
    }
  };
  

  return (
    <div className="flex justify-center items-center min-h-screen bg-gray-100">
      <div className="w-full max-w-lg bg-white shadow-lg rounded-lg p-8">
        <h2 className="text-2xl font-bold mb-6 text-center">Create Booking</h2>
        
        <form onSubmit={handleSubmit} className="space-y-4">
          {/* Check-in Date */}
          <div>
            <label className="block text-gray-700">Check-in Date</label>
            <input
              type="date"
              name="checkInDate"
              value={formData.checkInDate}
              onChange={handleChange}
              required
              className="mt-1 block w-full p-2 border border-gray-300 rounded-md"
            />
          </div>

          {/* Check-out Date */}
          <div>
            <label className="block text-gray-700">Check-out Date</label>
            <input
              type="date"
              name="checkOutDate"
              value={formData.checkOutDate}
              onChange={handleChange}
              required
              className="mt-1 block w-full p-2 border border-gray-300 rounded-md"
            />
          </div>

          {/* Quantity Room */}
          <div>
            <label className="block text-gray-700">Room Quantity</label>
            <input
              type="number"
              name="quantityRoom"
              value={formData.quantityRoom}
              onChange={handleChange}
              required
              min="1"
              className="mt-1 block w-full p-2 border border-gray-300 rounded-md"
            />
          </div>

          {/* Quantity Customer */}
          <div>
            <label className="block text-gray-700">Number of Customers</label>
            <input
              type="number"
              name="quantityCustomer"
              value={formData.quantityCustomer}
              onChange={handleChange}
              required
              min="1"
              className="mt-1 block w-full p-2 border border-gray-300 rounded-md"
            />
          </div>

          {/* Room Type */}
          <div>
          <label className="block text-gray-700">Room Type</label>
          <select
            name="roomType"
            value={formData.roomType}
            onChange={handleChange}
            required
            className="mt-1 block w-full p-2 border border-gray-300 rounded-md"
          >
            <option value="" disabled>
              Select Room Type
            </option>
            {roomTypes.map((type) => (
              <option key={type.roomTypeId} value={type.roomTypeId}>
                {type.roomTypeName}
              </option>
            ))}
          </select>
        </div>


          {/* Customer Email */}
          <div>
            <label className="block text-gray-700">Email</label>
            <input
              type="email"
              name="customerEmail"
              value={formData.customerEmail}
              onChange={handleChange}
              required
              className="mt-1 block w-full p-2 border border-gray-300 rounded-md"
            />
          </div>

          {/* Customer Phone Number */}
          <div>
            <label className="block text-gray-700">Phone Number</label>
            <input
              type="text"
              name="customerPhoneNumber"
              value={formData.customerPhoneNumber}
              onChange={handleChange}
              required
              className="mt-1 block w-full p-2 border border-gray-300 rounded-md"
            />
          </div>

          {/* Customer Name */}
          <div>
            <label className="block text-gray-700">Full Name</label>
            <input
              type="text"
              name="customerName"
              value={formData.customerName}
              onChange={handleChange}
              required
              className="mt-1 block w-full p-2 border border-gray-300 rounded-md"
            />
          </div>

          {/* Customer Identification */}
          <div>
            <label className="block text-gray-700">Identification</label>
            <input
              type="text"
              name="customerIdentification"
              value={formData.customerIdentification}
              onChange={handleChange}
              required
              className="mt-1 block w-full p-2 border border-gray-300 rounded-md"
            />
          </div>

          {/* Submit Button */}
          <div>
            <button
              type="submit"
              disabled={isLoading}
              className="w-full bg-blue-500 text-white py-2 px-4 rounded-md hover:bg-blue-600 transition"
            >
              {isLoading ? 'Booking...' : 'Create Booking'}
            </button>
          </div>
        </form>

        {/* Response Message */}
        {responseMessage && (
          <div className="mt-4 text-center text-red-600">
            {responseMessage}
          </div>
        )}
      </div>
    </div>
  );
};

export default Booking;