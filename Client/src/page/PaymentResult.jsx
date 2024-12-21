import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";

const PaymentResult = () => {
  const location = useLocation();
  const [transactionData, setTransactionData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const amount = params.get("vnp_Amount");
    const bankCode = params.get("vnp_BankCode");
    const orderInfo = params.get("vnp_OrderInfo");
    const responseCode = params.get("vnp_ResponseCode");

    if (!amount || !bankCode || !orderInfo || !responseCode) {
      setError("Missing required payment details in the URL.");
      setLoading(false);
      return;
    }  

    const fetchData = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/api/payment/transaction?vnp_Amount=${amount}&vnp_BankCode=${bankCode}&vnp_OrderInfo=${orderInfo}&vnp_ResponseCode=${responseCode}`
        ,{
          method: "GET"
        });

        if (response.ok) {
          const data = await response.json();
          setTransactionData(data);
        } else {
          const errorMessage = await response.text();
          setError(errorMessage || "Failed to fetch transaction data.");
        }
      } catch (err) {
        setError("An error occurred while fetching transaction data.");
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  if (loading) {
    return <div className="text-center">Loading...</div>;
  }

  if (error) {
    return <div className="text-center text-red-500">Error: {error}</div>;
  }

  return (
    <div className="container mx-auto p-6">
      <h1 className="text-3xl font-bold text-center mb-4">Transaction Result</h1>

      {transactionData ? (
  <div className="bg-white p-4 rounded shadow">
    <p className="text-lg font-medium">
      <span className="font-bold">Bank Code:</span> {transactionData.bankCode}
    </p>
    <p className="text-lg font-medium">
      <span className="font-bold">Amount:</span> {new Intl.NumberFormat("vi-VN", {
          style: "currency",
          currency: "VND",
        }).format(transactionData.amount)}
    </p>
    <p className="text-lg font-medium">
      <span className="font-bold">Order Info:</span> {transactionData.orderInfo}
    </p>
    <p className="text-lg font-medium text-green-600">
      <span className="font-bold">Status:</span> {transactionData.responseCode}
    </p>

    {/* Hiển thị số phòng */}
    <div className="mt-4">
      <span className="font-bold">Rooms:</span>
      <ul className="list-disc pl-6">
        {transactionData.roomNumber && transactionData.roomNumber.length > 0 ? (
          transactionData.roomNumber.map((room, index) => (
            <li key={index} className="text-lg font-medium">
              {room}
            </li>
          ))
        ) : (
          <li className="text-lg font-medium text-red-600">No rooms assigned.</li>
        )}
      </ul>
    </div>
  </div>
) : (
  <p className="text-center">No transaction data available.</p>
)}
    </div>
  );
};

export default PaymentResult;
