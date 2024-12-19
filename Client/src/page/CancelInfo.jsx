import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

const CancelInfo = () => {
  const [bookingData, setBookingData] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true); // Trạng thái tải dữ liệu
  const { bookingId } = useParams();

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(`http://localhost:8080/api/booking/getBooking/${bookingId}`, {
          method: "GET",
        });

        if (response.ok) {
          const data = await response.json(); // Trích xuất JSON từ response
          setBookingData(data);
        } else {
          // Nếu API trả về lỗi, xử lý lỗi
          const errorData = await response.json();
          setError(errorData.message || "Failed to fetch booking data");
        }
      } catch (error) {
        setError(error.message || "Something went wrong");
      } finally {
        setLoading(false); // Tắt trạng thái tải
      }
    };

    fetchData();
  }, [bookingId]);

  if (loading) {
    return <div>Loading...</div>; // Hiển thị trạng thái loading
  }

  if (error) {
    return <div>Error: {error}</div>; // Hiển thị lỗi nếu xảy ra
  }

  if (!bookingData) {
    return <div>No booking data found</div>; // Hiển thị nếu không có dữ liệu
  }

  return (
    <div className="min-h-screen flex justify-center items-center bg-gray-100 py-12 px-4 sm:px-6 lg:px-8">
      <div className="max-w-2xl w-full bg-white shadow-lg rounded-lg p-8">
        <h2 className="text-2xl font-semibold text-center text-gray-800 mb-6">
          Your booking was cancelled
        </h2>

        <div className="space-y-4">
          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium text-gray-600">Booking Id</label>
              <div className="mt-1 text-lg text-gray-800">{bookingData.bookingId}</div>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-600">Your Name</label>
              <div className="mt-1 text-lg text-gray-800">{bookingData.customerName}</div>
            </div>
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div>
              <label className="block text-sm font-medium text-gray-600">Booking Date</label>
              <div className="mt-1 text-lg text-gray-800">
                {new Date(bookingData.bookingDate).toLocaleDateString("vi-VN", {
                                    weekday: "long", // Hiển thị thứ
                                    year: "numeric", // Hiển thị năm
                                    month: "long", // Hiển thị tháng dạng tên (ví dụ: Tháng Mười Hai)
                                    day: "numeric", // Hiển thị ngày
                })}
              </div>
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-600">Cancel Date</label>
              <div className="mt-1 text-lg text-gray-800">
                            {new Date(bookingData.cancelDate).toLocaleDateString("vi-VN", {
                                weekday: "long", // Hiển thị thứ
                                year: "numeric", // Hiển thị năm
                                month: "long", // Hiển thị tháng dạng tên (ví dụ: Tháng Mười Hai)
                                day: "numeric", // Hiển thị ngày
                            })}
              </div>
            </div>
          </div>

          <div>
            <label className="block text-sm font-medium text-gray-600">Refund Fee</label>
            <div className="mt-1 text-lg text-gray-800">
                {new Intl.NumberFormat("vi-VN", {
                style: "currency",
                currency: "VND",
                }).format(bookingData.cancelFee)}
            </div>
            </div>

          <div>
            <label className="block text-sm font-medium text-gray-600">Status</label>
            <div className="mt-1 text-lg text-red-500">{bookingData.status}</div>
          </div>
        </div>

        <div className="mt-6 text-center">
          <button
            className="px-6 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700"
            onClick={() => (window.location.href = "/")} // Điều hướng về trang chủ
          >
            Return to Home Page
          </button>
        </div>
      </div>
    </div>
  );
};

export default CancelInfo;
