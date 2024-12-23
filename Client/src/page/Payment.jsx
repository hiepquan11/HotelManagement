import { useEffect } from "react";
import { useParams } from "react-router-dom";

const Payment = () =>{
    const {id} = useParams()
    useEffect(() => {
        const fetchData = async () =>{
            try {
                const response = await fetch(`http://localhost:8080/api/payment/createPayment?bookingId=${id}`, {
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

              }   
        }
        fetchData();
    })
}
export default Payment;