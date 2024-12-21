import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

const ViewBookingDetail = () => {
  const { bookingid } = useParams(); // Lấy bookingId từ URL
  const [bookingDetails, setBookingDetails] = useState(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchBookingDetails = async () => {
      setIsLoading(true);
      setError(null);
      try {
        const response = await fetch(
          `http://localhost:8080/api/booking/getInfoCustomer/${bookingid}`,
          {
            method: "GET",
            headers: {
              Authorization: `Bearer ${localStorage.getItem("jwt")}`,
            },
          }
        );

        if (!response.ok) {
          throw new Error("Không thể tải chi tiết đặt phòng");
        }

        const data = await response.json();
        setBookingDetails(data);
      } catch (error) {
        setError(error.message);
      } finally {
        setIsLoading(false);
      }
    };

    fetchBookingDetails();
  }, [bookingid]);

  if (isLoading) {
    return <div className="text-center p-6">Đang tải chi tiết đặt phòng...</div>;
  }

  if (error) {
    return (
      <div className="text-center p-6 text-red-500">
        Đã xảy ra lỗi: {error}
      </div>
    );
  }

  if (!bookingDetails) {
    return <div className="text-center p-6">Không tìm thấy chi tiết đặt phòng.</div>;
  }

  const {
    bookingId: id,
    customerName,
    cancelDate,
    bookingDate,
    status,
    cancelFee,
  } = bookingDetails;

  return (
    <div className="min-h-screen bg-gray-100 p-6">
      <div className="max-w-4xl mx-auto bg-white shadow-lg rounded-lg">
        <header className="p-4 border-b border-gray-200">
          <h1 className="text-2xl font-bold text-gray-700">
            Chi tiết đặt phòng (ID: {id})
          </h1>
        </header>
        <div className="p-6 space-y-4">
          <p>
            <strong>Customer:</strong> {customerName}
          </p>
          <p>
            <strong>Booking Date:</strong> {new Date(bookingDate).toLocaleDateString()}
          </p>
          <p>
            <strong>Status:</strong> {status}
          </p>
          <p>
            <strong>Cancel Date:</strong>{" "}
            {cancelDate
              ? new Date(cancelDate).toLocaleDateString()
              : "Chưa hủy"}
          </p>
          <p>
            <strong>Cancel Fee:</strong>{" "}
            {cancelFee
              ? `${cancelFee.toLocaleString("vi-VN")} VND`
              : "Không áp dụng"}
          </p>
        </div>
      </div>
    </div>
  );
};

export default ViewBookingDetail;
