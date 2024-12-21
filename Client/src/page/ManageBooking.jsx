import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

const ManageBooking = () => {
  const [currentPage, setCurrentPage] = useState(0);
  const [listBookings, setListBookings] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);
  const pageSize = 10;
  const navigate = useNavigate();       

  const handlePageChange = (page) => {
    setCurrentPage(page);
  };

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      setError(null);
      try {
        const response = await fetch(
          `http://localhost:8080/api/booking/getAll?page=${currentPage}&size=${pageSize}`,
          {
            method: "GET",
            headers: {
              Authorization: `Bearer ${localStorage.getItem("jwt")}`,
            },
          }
        );

        if (!response.ok) {
          throw new Error("Không thể tải dữ liệu");
        }

        const data = await response.json();
        setListBookings(data);
      } catch (error) {
        setError(error.message);
      } finally {
        setIsLoading(false);
      }
    };

    fetchData();
  }, [currentPage]);

  if (isLoading) {
    return <div className="text-center p-6">Loading Data...</div>;
  }

  if (error) {
    return (
      <div className="text-center p-6 text-red-500">
        Đã xảy ra lỗi: {error}
      </div>
    );
  }

  if (!listBookings) {
    return <div className="text-center p-6">No data.</div>;
  }

  const { content = [], pageable = {}, totalPages = 1 } = listBookings;
  const handleViewDetail = (id) =>{
    navigate(`/detail/${id}`)
  }

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <div className="max-w-6xl mx-auto bg-white shadow-lg rounded-lg">
        <header className="p-4 border-b border-gray-200">
          <h1 className="text-2xl font-bold text-gray-700">List Booking</h1>
        </header>
        <div className="p-4">
          {content.length === 0 ? (
            <div className="text-center text-gray-500">
              No booking.
            </div>
          ) : (
            <table className="table-auto w-full text-sm text-left">
              <thead>
                <tr className="bg-gray-200 text-gray-600 uppercase text-xs">
                  <th className="px-4 py-2">ID</th>
                  <th className="px-4 py-2">Booking Date</th>
                  <th className="px-4 py-2">Status</th>
                  <th className="px-4 py-2">Check-in</th>
                  <th className="px-4 py-2">Check-out</th>
                  <th className="px-4 py-2">Total Amount</th>
                  <th className="px-4 py-2">Cancel Fee</th>
                  <th className="px-4 py-2">Action</th>
                </tr>
              </thead>
              <tbody>
                {content.map((booking, index) => (
                  <tr
                    key={index}
                    className="border-b border-gray-200 hover:bg-gray-50"
                  >
                    <td className="px-4 py-2">{booking.bookingId}</td>
                    <td className="px-4 py-2">
                      {new Date(booking.bookingDate).toLocaleDateString()}
                    </td>
                    <td className="px-4 py-2">{booking.bookingStatus}</td>
                    <td className="px-4 py-2">
                      {new Date(booking.checkInDate).toLocaleDateString()}
                    </td>
                    <td className="px-4 py-2">
                      {new Date(booking.checkOutDate).toLocaleDateString()}
                    </td>
                    <td className="px-4 py-2">
                      {booking.totalAmount.toLocaleString("vi-VN")} VND
                    </td>
                    <td className="px-4 py-2">
                      {booking.cancelFee.toLocaleString("vi-VN")} VND
                    </td>
                    <td className="px-4 py-2">
                      <button
                        className="px-3 py-1 bg-blue-500 text-white rounded hover:bg-blue-600"
                        onClick={() => handleViewDetail(booking.bookingId)}
                      >
                        View Detail
                      </button>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
        <footer className="p-4 border-t border-gray-200">
          <div className="flex justify-between items-center">
            <button
              className={`px-4 py-2 bg-blue-500 text-white rounded ${
                pageable.pageNumber === 0 ? "opacity-50 cursor-not-allowed" : ""
              }`}
              onClick={() => handlePageChange(pageable.pageNumber - 1)}
              disabled={pageable.pageNumber === 0}
            >
              Previous
            </button>
            <span>
              Page {pageable.pageNumber + 1} / {totalPages}
            </span>
            <button
              className={`px-4 py-2 bg-blue-500 text-white rounded ${
                pageable.pageNumber === totalPages - 1
                  ? "opacity-50 cursor-not-allowed"
                  : ""
              }`}
              onClick={() => handlePageChange(pageable.pageNumber + 1)}
              disabled={pageable.pageNumber === totalPages - 1}
            >
              Next
            </button>
          </div>
        </footer>
      </div>
    </div>
  );
};

export default ManageBooking;
