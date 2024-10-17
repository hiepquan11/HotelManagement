import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // Updated import
import Header from './page/Header';
import Home from './page/Home';
import Rooms from './page/Rooms';
import Booking from './page/Booking';
import ContactUs from './page/ContactUs';
import SignIn from './page/SignIn'; // Import SignIn

function App() {
  return (
    <Router>
      <div className="App">
        <Header />
        <Routes> {/* Replaced Switch with Routes */}
          <Route path="/" element={<Home />} /> {/* Use 'element' instead of 'component' */}
          <Route path="/rooms" element={<Rooms />} />
          <Route path="/booking" element={<Booking />} />
          <Route path="/Contact_us" element={<ContactUs />} />
          <Route path="/signin" element={<SignIn />} /> {/* Route for SignIn */}
        </Routes>
      </div>
    </Router>
  );
}

export default App;

