import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

const BookingDetail = () =>{
    const {id} = useParams();
    const [bookingDetails, setBookingDetails] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() =>{
        const fetchBookingDetail = async () =>{
            try {
                setLoading(true);
                const response = await fetch(`http://localhost:8080/api/customer/bookingDetail/${id}`,{
                    method: "GET"
                });

                if(response.ok){
                    const data = await response.json();
                    setBookingDetails(data);
                } else {
                    throw new Error('failed to fetch booking details');
                }
            } catch (error) {
                setError(error);
            } finally {
                setLoading(false);
            }
        }
        fetchBookingDetail();
    },[id])

    if (loading) {
        return (
          <div className="flex items-center justify-center min-h-screen">
            <p className="text-lg font-medium text-gray-500">Loading...</p>
          </div>
        );
      }
    
      if (error) {
        return (
          <div className="flex items-center justify-center min-h-screen">
            <p className="text-lg font-medium text-red-500">{error}</p>
          </div>
        );
      }
    
      return (
        <div className="p-6 max-w-6xl mx-auto bg-white shadow-md rounded-md">
          <h2 className="text-2xl font-bold mb-4">Booking Details</h2>
    
          {/* Table for room details */}
          <table className="w-full border-collapse border border-gray-300">
            <thead>
              <tr className="bg-gray-100">
                <th className="border border-gray-300 p-2 text-left">Room Number</th>
                <th className="border border-gray-300 p-2 text-left">Bed Quantity</th>
                <th className="border border-gray-300 p-2 text-left">Price</th>
                <th className="border border-gray-300 p-2 text-left">Status</th>
                <th className="border border-gray-300 p-2 text-left">Description</th>
              </tr>
            </thead>
            <tbody>
              {bookingDetails.map((detail) => (
                <tr key={detail.bookingDetailId} className="hover:bg-gray-50">
                  <td className="border border-gray-300 p-2">{detail.room.roomNumber}</td>
                  <td className="border border-gray-300 p-2">{detail.room.bedQuantity}</td>
                  <td className="border border-gray-300 p-2">{detail.price.toLocaleString("vi-VN", { style: "currency", currency: "VND" })}</td>
                  <td className="border border-gray-300 p-2">
                    <span
                      className={`px-2 py-1 rounded text-white ${
                        detail.room.status === "BOOKED" ? "bg-green-500" : "bg-gray-500"
                      }`}
                    >
                      {detail.room.status}
                    </span>
                  </td>
                  <td className="border border-gray-300 p-2">{detail.room.description}</td>
                </tr>
              ))}
            </tbody>
          </table>
    
          {/* Action Buttons */}
          <div className="mt-6 flex justify-end gap-4">
            <button
              className="bg-red-500 text-white py-2 px-4 rounded-md hover:bg-red-600 transition"
              onClick={() => alert("Cancel booking confirmed!")}
            >
              Cancel Booking
            </button>
          </div>
        </div>
      );
}

export default BookingDetail;