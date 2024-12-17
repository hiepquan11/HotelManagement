
import React, { useContext} from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "./AuthContext";

const Header = () => {
  const { isAuthenticated, logout, user } = useContext(AuthContext); // Use AuthContext
  const navigate = useNavigate();



  const handleSignOut = () => {
    logout(); // Call the logout function from AuthContext
    navigate("/"); // Redirect to home
  };

  return (
    <header className="flex flex-col md:flex-row justify-between items-center bg-gradient-to-r from-gray-700 via-gray-900 to-black p-6 md:p-4 shadow-md">
      <div className="flex items-center mb-4 md:mb-0">
        <h1 className="text-white text-2xl font-extrabold tracking-wide">Management Hotel</h1>
      </div>
      <nav className="space-x-4 md:space-x-8 text-center">
        <a href="/" className="text-gray-300 hover:text-white hover:underline transition-all duration-300">
          Home
        </a>
        <a href="/Rooms" className="text-gray-300 hover:text-white hover:underline transition-all duration-300">
          Rooms
        </a>
        <a href="/Booking" className="text-gray-300 hover:text-white hover:underline transition-all duration-300">
          Booking
        </a>
        <a href="/Contact_us" className="text-gray-300 hover:text-white hover:underline transition-all duration-300">
          Contact Us
        </a>
        {isAuthenticated && user?.role === "ADMIN" && (
          <a
            href="/AddRoom"
            className="text-gray-300 hover:text-white hover:underline transition-all duration-300"
          >
            Add Room
          </a>
        )}
         {isAuthenticated && (
          <a
            href="/YourBooking"
            className="text-gray-300 hover:text-white hover:underline transition-all duration-300"
          >
            Your Booking
          </a>
        )}
        {isAuthenticated && (
          <a
            href="/RoomTypeDetail"
            className="text-gray-300 hover:text-white hover:underline transition-all duration-300"
          >
            RoomTypeDetail
          </a>
        )}
      </nav>
      {isAuthenticated ? (
        <button
          className="bg-red-600 hover:bg-red-700 text-white px-6 py-3 rounded-full mt-4 md:mt-0 shadow-lg"
          onClick={handleSignOut}
        >
          Sign Out
        </button>
      ) : (
        <button
          className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-3 rounded-full mt-4 md:mt-0 shadow-lg"
          onClick={() => navigate("/signin")}
        >
          Sign In
        </button>
      )}
    </header>
  );
};

export default Header;