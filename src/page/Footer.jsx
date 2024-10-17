// src/components/Footer.js
import React from 'react';

const Footer = ({ socialLinks }) => {
  return (
    <footer className="footer">
      <p>&copy; {new Date().getFullYear()} MyApp. All rights reserved.</p>
      <div className="social-links">
        {socialLinks && socialLinks.map((link, index) => (
          <a key={index} href={link.url} target="_blank" rel="noopener noreferrer">
            {link.name}
          </a>
        ))}
      </div>
    </footer>
  );
};

export default Footer;
