import React from 'react';


const ContactUs = () => {
  return (
    <div className="bg-gray-100 py-10">
      <div className="container mx-auto px-4">
        {/* Title Section */}
        <div className="text-center mb-8">
          <h1 className="text-4xl font-bold text-blue-600">About Hotel Management</h1>
          <p className="text-gray-600 mt-2">
            Discover how our Hotel Management system simplifies your operations and enhances guest satisfaction.
          </p>
        </div>

        {/* Content Section */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8 items-center">
          {/* Image */}
          <div>
            <img
              src="https://via.placeholder.com/500x300"
              alt="Hotel Management"
              className="rounded-lg shadow-lg"
            />
          </div>

          {/* Text Content */}
          <div>
            <h2 className="text-2xl font-semibold text-gray-700 mb-4">
              What is Hotel Management?
            </h2>
            <p className="text-gray-600 leading-relaxed mb-4">
              Hotel Management systems are designed to streamline operations, improve efficiency, and provide a better experience for both staff and guests. From room bookings to housekeeping, our solution ensures seamless management of all hotel activities.
            </p>

            <h3 className="text-xl font-semibold text-gray-700 mb-2">Key Benefits:</h3>
            <ul className="list-disc list-inside text-gray-600 space-y-2">
              <li>Streamlined room booking process</li>
              <li>Enhanced guest satisfaction</li>
              <li>Automated reporting and analytics</li>
              <li>Improved communication across departments</li>
            </ul>
          </div>
        </div>

        {/* Footer Section */}
        <div className="text-center mt-10">
          <h2 className="text-xl font-semibold text-blue-500">
            Ready to enhance your hotel's operations?
          </h2>
          <p className="text-gray-600 mt-2">
            Get in touch with us today to learn how our Hotel Management system can help you.
          </p>
        </div>
      </div>
    </div>
  );
};


export default ContactUs;
