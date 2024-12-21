import React, { useContext, useEffect, useState } from 'react';
import { AuthContext } from './AuthContext';
import { useNavigate } from 'react-router-dom';

const YourBooking = () => {
  const { isAuthenticated, user } = useContext(AuthContext);
  const [bookings, setBookings] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [modalVisible, setModalVisible] = useState(false);
  const [selectedBooking, setSelectedBooking] = useState(null);
  const [currentPage, setCurrentPage] = useState(0);
  const [pageSize] = useState(5);
  const navigate = useNavigate();

  const handleViewDetails = (bookingId) => {
    navigate(`/BookingDetail/${bookingId}`);
  };

  const handlePaymentClick = async (bookingId) => {
    try {
      const response = await fetch(`http://localhost:8080/api/payment/createPayment?bookingId=${bookingId}`, {
        method: 'GET',
      });
      if (response.ok) {
        const data = await response.json();
        const paymentLink = data.url;
        if (paymentLink) {
          window.location.href = paymentLink;
        } else {
          alert('Payment link not available');
        }
      } else {
        throw new Error('Failed to initiate payment');
      }
    } catch (error) {
      setError(error);
    }
  };

  const handleCancelBooking = async () => {
    try {
      const response = await fetch(
        `http://localhost:8080/api/customer/${parseInt(user.id)}/cancelBooking/${parseInt(selectedBooking)}`,
        {
          method: 'PUT',
          headers: {
            Authorization: `Bearer ${localStorage.getItem('jwt')}`,
          },
        }
      );

      if (response.ok) {
        alert('Booking has been canceled successfully.');
        setModalVisible(false);
        navigate(`/cancel/${selectedBooking}`);
        window.location.reload();
      } else {
        const errorData = await response.json();
        alert(errorData?.message || 'Failed to cancel booking.');
      }
    } catch (error) {
      console.error('Error cancelling booking:', error);
      alert('An error occurred while cancelling the booking.');
    }
  };

  const viewCancelbooking = (bookingId) =>{
    navigate(`/cancel/${bookingId}`)
  }

  const openModal = (bookingId) => {
    setSelectedBooking(bookingId);
    setModalVisible(true);
  };

  const closeModal = () => {
    setModalVisible(false);
    setSelectedBooking(null);
  };

  useEffect(() => {
    if (isAuthenticated && user) {
      const fetchBooking = async () => {
        try {
          const response = await fetch(`http://localhost:8080/api/customer/bookings?page=${currentPage}&size=${pageSize}`, {
            headers: {
              Authorization: `Bearer ${localStorage.getItem('jwt')}`,
            },
          });

          if (!response.ok) {
            throw new Error('Failed to fetch bookings');
          }

          const data = await response.json();
          setBookings(data);
        } catch (error) {
          setError('Could not load your booking. Please try again');
          console.log(error);
        } finally {
          setLoading(false);
        }
      };
      fetchBooking();
    }
  }, [isAuthenticated, user, currentPage, pageSize]);

  return (
    <div className="p-6 bg-gray-100 min-h-screen">
      <h1 className="text-2xl font-bold text-center mb-6">Your Bookings</h1>

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
            {bookings.map((booking, index) => (
              <tr key={index} className="border-b hover:bg-gray-100">
                <td className="px-6 py-4">{booking.bookingId}</td>
                <td className="px-6 py-4">{new Date(booking.checkInDate).toLocaleDateString()}</td>
                <td className="px-6 py-4">{new Date(booking.checkOutDate).toLocaleDateString()}</td>
                <td className="px-6 py-4">{booking.roomType}</td>
                <td className="px-6 py-4">{booking.quantityRoom}</td>
                <td className="px-6 py-4">
                  <span
                    className={`px-2 py-1 rounded ${
                      booking.bookingStatus === 'APPROVE'
                        ? 'bg-green-200 text-green-800'
                        : booking.bookingStatus === 'PENDING'
                        ? 'bg-yellow-200 text-yellow-800'
                        : 'bg-red-200 text-red-800'
                    }`}
                  >
                    {booking.bookingStatus}
                  </span>
                </td>
                <td className="px-6 py-4 flex gap-2">
                   {/* Hiển thị nút "View Details" nếu trạng thái booking không phải PENDING và không có bookingDetails */}
                    {booking.bookingStatus !== "PENDING" && booking.bookingDetails && booking.bookingStatus !== "CANCELLED" && (
                      <button
                        onClick={() => handleViewDetails(booking.bookingId)}
                        className="bg-blue-500 text-white px-3 py-1 rounded hover:bg-blue-600"
                      >
                        View Details
                      </button>
                    )}

                    {/* Hiển thị nút "View Cancelled Info" nếu trạng thái booking là CANCELLED */}
                    {booking.bookingStatus === "CANCELLED" && (
                      <button
                        onClick={() => viewCancelbooking(booking.bookingId)}
                        className="bg-blue-500 text-white px-3 py-1 rounded hover:bg-blue-600"
                      >
                        View Cancelled Info
                      </button>
                    )}
                    {booking.bookingStatus !== 'CANCELLED' && (
                      <button
                        onClick={() => openModal(booking.bookingId)}
                        className="bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600"
                      >
                        Cancel
                      </button>
                    )}
                  {!booking.payment && booking.bookingStatus !== "CANCELLED" && (
                    <button
                      onClick={() => handlePaymentClick(booking.bookingId)}
                      className="bg-green-500 text-white px-3 py-1 rounded hover:bg-green-600"
                    >
                      Payment
                    </button>
                  )}
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}

      {/* Modal */}
      {modalVisible && (
        <div className="fixed inset-0 bg-gray-800 bg-opacity-50 flex justify-center items-center">
          <div className="bg-white rounded-lg shadow-lg p-6 w-96">
            <h2 className="text-lg font-bold mb-4">Confirm Cancellation</h2>
            <p>Are you sure you want to cancel this booking?</p>
            <div className="flex justify-end mt-6 gap-3">
              <button
                onClick={closeModal}
                className="bg-gray-300 text-gray-700 px-4 py-2 rounded hover:bg-gray-400"
              >
                No
              </button>
              <button
                onClick={handleCancelBooking}
                className="bg-red-500 text-white px-4 py-2 rounded hover:bg-red-600"
              >
                Yes, Cancel
              </button>
            </div>
          </div>
        </div>
      )}
      <div className="flex justify-center items-center gap-4 mt-4">
            <button
              onClick={() => setCurrentPage((prev) => Math.max(prev - 1, 0))}
              disabled={currentPage === 0}
              className={`px-4 py-2 rounded ${currentPage === 0 ? 'bg-gray-300' : 'bg-blue-500 text-white hover:bg-blue-600'}`}
            >
              Previous
            </button>
            <span>Page {currentPage + 1}</span>
            <button
              onClick={() => setCurrentPage((prev) => prev + 1)}
              className="px-4 py-2 rounded bg-blue-500 text-white hover:bg-blue-600"
            >
              Next
            </button>
          </div>
      
    </div>
  );
};

export default YourBooking;
