import React, { useContext, useEffect, useState } from 'react';
import { AuthContext } from './AuthContext';
import { useNavigate } from 'react-router-dom';

const YourBooking = () => {
  // Dummy data for bookings
//   const bookings = [
//     {
//       bookingId: '1',
//       checkInDate: '2024-12-20',
//       checkOutDate: '2024-12-25',
//       roomTypeName: 'Deluxe Room',
//       quantityRoom: 2,
//       status: 'Confirmed',
//     },
//     {
//       bookingId: '2',
//       checkInDate: '2024-12-22',
//       checkOutDate: '2024-12-28',
//       roomTypeName: 'Standard Room',
//       quantityRoom: 1,
//       status: 'Pending',
//     },
//     {
//       bookingId: '3',
//       checkInDate: '2024-12-15',
//       checkOutDate: '2024-12-18',
//       roomTypeName: 'Suite',
//       quantityRoom: 1,
//       status: 'Cancelled',
//     },
//   ];

const {isAuthenticated, user} = useContext(AuthContext);
const [bookings, setBookings] = useState([]);
const [loading, setLoading] = useState(true);
const [error, setError] = useState('');
const [selectedBooking, setSelectedBooking] = useState(null);
const navigate = useNavigate();


const handleViewDetails = (bookingid) =>{
  navigate(`/BookingDetail/${bookingid}`);
}

const handleCancelBooking = async (bookingId) => {
  const confirmedCancel = window.confirm('Are you sure you want to cancel this booking')
  if(confirmedCancel){
    try {
      const response = await fetch(
        `http://localhost:8080/api/customer/${parseInt(user.id)}/cancelBooking/${parseInt(bookingId)}`,
        {
          method: 'PUT  ',
          headers: {
            Authorization: `Bearer ${localStorage.getItem('jwt')}`,
          },
        }
      );
  
      if (response.ok) {
        alert('Booking has been canceled successfully.');
        // Cập nhật danh sách booking (có thể gọi lại API để load danh sách mới)
        setBookings((prevBookings) => prevBookings.filter((b) => b.bookingId !== bookingId));
      } else {
        // Xử lý lỗi từ API
        const errorData = await response.json();
        alert(errorData?.message || 'Failed to cancel booking.');
      }
    } catch (error) {
      console.error('Error cancelling booking:', error);
      alert('An error occurred while cancelling the booking.');
    }
  }
 
};

useEffect(() => {
    if(isAuthenticated && user){
        const fetchBooking = async () =>{
            try {
                const response = await fetch(`http://localhost:8080/api/customer/bookings?page=0&size=5`, {
                    headers: {
                        Authorization: `Bearer ${localStorage.getItem('jwt')}`
                    },
                });

                if(!response.ok){
                    throw new Error('failed to fetch bookings');
                }

                const data = await response.json();
                setBookings(data);

            } catch (error) {
                setError('Could not load your booking. Please try again');
                console.log(error);
            } finally{
                setLoading(false);
            };
        }
        fetchBooking();
    }
},[isAuthenticated, user])
  return (
    <div className="p-6 bg-gray-100 min-h-screen">
      <h1 className="text-2xl font-bold text-center mb-6">Your Bookings</h1>
      
      {/* If no bookings */}
      {bookings.length === 0 ? (
        <p className="text-center">You have no bookings yet.</p>
      ) : (
        <table className="min-w-full bg-white shadow-md rounded-lg overflow-hidden">
          <thead>
            <tr className="bg-blue-500 text-white">
              <th className="px-6 py-3 text-left">Booking ID</th>
              <th className="px-6 py-3 text-left">Check-in</th>
              <th className="px-6 py-3 text-left">Check-out</th>
              <th className="px-6 py-3 text-left">Room Type</th>
              <th className="px-6 py-3 text-left">Quantity</th>
              <th className="px-6 py-3 text-left">Status</th>
            </tr>
          </thead>
          <tbody>
            {/* Loop through bookings data */}
            {bookings.map((booking, index) => (
              <tr key={index} className="border-b">
                <td className="px-6 py-4">{booking.bookingId}</td>
                <td className="px-6 py-4">{new Date(booking.checkInDate).toLocaleDateString()}</td>
                <td className="px-6 py-4">{new Date(booking.checkOutDate).toLocaleDateString()}</td>
                <td className="px-6 py-4">{booking.roomType}</td>
                <td className="px-6 py-4">{booking.quantityRoom}</td>
                <td className="px-6 py-4">
                  <span
                    className={`px-2 py-1 rounded ${
                      booking.bookingStatus === 'APPROVE' ? 'bg-green-200 text-green-800' : booking.bookingStatus === 'PENDING' ? 'bg-yellow-200 text-yellow-800' : 'bg-red-200 text-red-800'
                    }`}
                  >
                    {booking.bookingStatus}
                  </span>
                </td>

                <td className="px-6 py-4 flex gap-2">
                  {/* View Details Button */}
                  <button
                    onClick={() => handleViewDetails(booking.bookingId)}
                    className="bg-blue-500 text-white px-3 py-1 rounded hover:bg-blue-600"
                  >
                    View Details
                  </button>
                  {/* Cancel Booking Button */}
                  {booking.status !== 'Cancelled' && (
                    <button
                      onClick={() => handleCancelBooking(booking.bookingId)}
                      className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600"
                    >
                      Cancel
                    </button>
                  )}
                </td>
              </tr>

            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default YourBooking;