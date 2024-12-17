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
import RoomTypeDetail from  "./Room/RoomTypeDetail";


function App() {
  return (
    <AuthProvider>
    <Router>
      <div className="App">
        <Header />
        <Routes> {/* Replaced Switch with Routes */}
          <Route path="/" element={<Home />} /> {/* Use 'element' instead of 'component' */}
          <Route path="/rooms" element={<Rooms />} />
          <Route path="/booking" element={<Booking />} />
          <Route path="/Contact_us" element={<ContactUs />} />
          <Route path='/YourBooking' element={<YourBooking/>}></Route>
          <Route path='/BookingDetail/:id' element={<BookingDetail/>}/>
          <Route path="/signin" element={<SignIn />} /> {/* Route for SignIn */}
          <Route path="/SignUp" element={<SignUp />} />
          <Route path="/AddRoom" element={<AddRoom />} />
          

         <Route path="/rooms/:id" element={<RoomDetails />} />
         <Route path="/RoomTypeDetail" element={<RoomTypeDetail />} />
         
        </Routes>
        <Footer />
      </div>
    </Router>
    </AuthProvider>
  );
}

export default App;