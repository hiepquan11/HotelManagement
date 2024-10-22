import React from 'react';

const Footer = () => {
  return (
    <footer className="bg-gray-800 text-white py-6 mt-10">
      <div className="container mx-auto flex flex-col md:flex-row justify-between items-center px-4">
        <div className="mb-4 md:mb-0">
          <h3 className="text-lg font-bold">Management Hotel</h3>
          <p className="text-gray-400">Your comfort is our priority.</p>
        </div>
        <nav className="mb-4 md:mb-0">
          <ul className="flex space-x-6">
            <li>
              <a href="/" className="hover:text-blue-400 transition-colors">Home</a>
            </li>
            <li>
              <a href="/rooms" className="hover:text-blue-400 transition-colors">Rooms</a>
            </li>
            <li>
              <a href="/booking" className="hover:text-blue-400 transition-colors">Booking</a>
            </li>
            <li>
              <a href="/contact_us" className="hover:text-blue-400 transition-colors">Contact Us</a>
            </li>
            <li>
              <a href="/signin" className="hover:text-blue-400 transition-colors">Sign In</a>
            </li>
            <li>
              <a href="/signup" className="hover:text-blue-400 transition-colors">Sign Up</a>
            </li>
          </ul>
        </nav>
        <div className="flex space-x-4">
  <a href="https://www.facebook.com" className="hover:text-blue-400 transition-colors">Facebook</a>
  <a href="https://www.twitter.com" className="hover:text-blue-400 transition-colors">Twitter</a>
  <a href="https://www.instagram.com" className="hover:text-blue-400 transition-colors">Instagram</a>
</div>

      </div>
      <div className="text-center mt-4">
        <p className="text-gray-400 text-sm">&copy; 2024 Management Hotel. All rights reserved.</p>
      </div>
    </footer>
  );
};

export default Footer;
