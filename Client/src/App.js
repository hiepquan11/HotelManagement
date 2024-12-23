import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // Updated import
import Header from './page/Header';
import Home from './page/Home';
import Rooms from './page/Rooms';
import Booking from './page/Booking';
import ContactUs from './page/ContactUs';
import SignIn from './page/SignIn'; // Import SignIn
import SignUp from './page/SignUp';
import Footer  from './page/Footer';
import { AuthProvider } from "./page/AuthContext";
import AddRoom from "./Room/AddRoom.jsx";
import RoomDetails from './page/RoomDetails';
import YourBooking from './page/YourBooking.jsx';
import BookingDetail from './page/BookingDetail.jsx';
import PaymentResult from './page/PaymentResult.jsx';
import RoomTypeDetail from  "./Room/RoomTypeDetail";
import CancelInfo from './page/CancelInfo.jsx';
import AddRoomForRoomtype from "./Room/AddRoomForRoomtype"
import RoomForRoomType from "./Room/RoomForRoomType"
import UpdateRoomType from './Room/UpdateRoomType.jsx';
import ManageBooking from './page/ManageBooking.jsx';
import ViewBookingDetail from './page/ViewBookingDetail.jsx';
import ManageCustomer from './page/ManageCustomer.jsx';
import ViewCustomerDetail from './page/ViewCustomerDetail.jsx';
import EditRoom from './Room/EditRoom';
import Payment from './page/Payment.jsx';



function App() {
  return (
    <AuthProvider>
    <Router>
      <div className="App">
        <Header />
        <div className="min-h-screen">
        <Routes> {/* Replaced Switch with Routes */}
          <Route path="/" element={<Home />} /> {/* Use 'element' instead of 'component' */}
          <Route path="/rooms" element={<Rooms />} />
          <Route path="/booking" element={<Booking />} />
          <Route path="/Contact_us" element={<ContactUs />} />
          <Route path='/YourBooking' element={<YourBooking/>}></Route>
          <Route path='/BookingDetail/:id' element={<BookingDetail/>}/>
          <Route path='/transaction' element={<PaymentResult/>}/>
          <Route path='/cancel/:bookingId' element={<CancelInfo/>}/>
          <Route path="/signin" element={<SignIn />} /> {/* Route for SignIn */}
          <Route path="/SignUp" element={<SignUp />} />
          <Route path="/AddRoom" element={<AddRoom />} />
          <Route path="/AddRoomForRoomTypeDetail" element={<AddRoomForRoomtype />} />
          <Route path='/Payment/:id' element={<Payment/>}/>
          
          <Route path="/RoomForRoomType" element={<RoomForRoomType />} />
          <Route path="/UpdateRoomType/:id" element={<UpdateRoomType/>}/>
          <Route path='/ManageBooking' element={<ManageBooking/>}/>
          <Route path='/ManageCustomer' element={<ManageCustomer/>}/>
          <Route path='/detail/:bookingid' element={<ViewBookingDetail/>}/>
          <Route path='/CustomerDetail/:accoundId' element={<ViewCustomerDetail/>}/>
          <Route path="/EditRoom" element={<EditRoom/>}/>
          

         <Route path="/rooms/:id" element={<RoomDetails />} />
         <Route path="/RoomTypeDetail" element={<RoomTypeDetail />} />
         
        </Routes>
        </div>
        <Footer />
      </div>
    </Router>
    </AuthProvider>
  );
}

export default App;