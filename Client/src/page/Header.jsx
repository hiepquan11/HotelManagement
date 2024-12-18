import React, { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
import { AuthContext } from "./AuthContext";

const Header = () => {
  const { isAuthenticated, logout, user } = useContext(AuthContext); // Use AuthContext
  const navigate = useNavigate();

  const [showRoomsMenu, setShowRoomsMenu] = useState(false); // State để toggle dropdown menu

  const handleSignOut = () => {
    logout(); // Call the logout function from AuthContext
    navigate("/"); // Redirect to home
  };


  return (
    <header className="flex flex-col md:flex-row justify-between items-center bg-gradient-to-r from-gray-700 via-gray-900 to-black p-6 md:p-4 shadow-md">
      <div className="flex items-center mb-4 md:mb-0">
        <h1 className="text-white text-2xl font-extrabold tracking-wide">Management Hotel</h1>
      </div>
      <nav className="relative space-x-4 md:space-x-8 text-center">
        <a
          href="/"
          className="text-gray-300 hover:text-white hover:underline transition-all duration-300"
        >
          Home
        </a>
        <a href="/Rooms" className="text-gray-300 hover:text-white hover:underline transition-all duration-300">
          Rooms
        </a>


        {/* Dropdown Menu cho Rooms */}
        <div className="inline-block relative">
  <button
    className="text-gray-300 hover:text-white hover:underline transition-all duration-300"
    onClick={() => setShowRoomsMenu(!showRoomsMenu)}
  >
    Rooms
  </button>
  {showRoomsMenu && (
    <div className="absolute bg-white shadow-lg rounded mt-2 py-2 w-48 text-left z-20">
      {/* RoomTypeDetail */}
      <a
        href="/RoomTypeDetail"
        className="block px-4 py-2 text-gray-700 hover:bg-gray-200"
      >
        Room Type Detail
      </a>
      {/* AddRoom chỉ khi user là ADMIN */}
      {isAuthenticated && user?.role === "ADMIN" && (
        <a
          href="/AddRoom"
          className="block px-4 py-2 text-gray-700 hover:bg-gray-200"
        >
          Add Room
        </a>
      )}
    </div>
  )}
</div>


        <a
          href="/Booking"
          className="text-gray-300 hover:text-white hover:underline transition-all duration-300"
        >
          Booking
        </a>
        <a
          href="/Contact_us"
          className="text-gray-300 hover:text-white hover:underline transition-all duration-300"
        >
          Contact Us
        </a>
        {isAuthenticated && (
          <a
            href="/YourBooking"
            className="text-gray-300 hover:text-white hover:underline transition-all duration-300"
          >
            Your Booking
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
