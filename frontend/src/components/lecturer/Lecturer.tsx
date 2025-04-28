import React, { useState } from 'react';
import axios from 'axios';
import './lecturer.css'

function LecturerDashboard() {
  const [courseId, setCourseId] = useState('');
  const [lecturerId, setLecturerId] = useState('');
  const [otp, setOtp] = useState('');
  const [error, setError] = useState('');



  const handleGenerateOTP = async () => {
    try {

       const token = localStorage.getItem('token'); 

       if (!token) {
            setError('Unauthorized. Please login first.');
            return;
        }

      const response = await axios.post('http://localhost:8082/api/users/generate', {
        courseId,
        lecturerId
      }, {
        headers: {
          Authorization: `Bearer ${token}`, 
          'Content-Type': 'application/json',
        },
      }
     );

      console.log('Response:', response.data);
      setOtp(response.data.qrData);
      setError('');
    } catch (error) {
      console.error(error);
      setError('Failed to generate OTP. Please try again.');
    }
  };
  

  return (
    <div className="dashboard-container">
      <h1 className="dashboard-title">Lecturer Dashboard</h1>
      
      <div className="form-container">
        <input
          type="text"
          placeholder="Enter Course ID"
          value={courseId}
          onChange={(e) => setCourseId(e.target.value)}
          className="input-field"
        />

        <input
          type="text"
          placeholder="Enter Lecturer ID"
          value={lecturerId}
          onChange={(e) => setLecturerId(e.target.value)}
          className="input-field"
        />

        <button className="generate-btn" onClick={handleGenerateOTP}>
          Generate OTP
        </button>
      </div>

      {otp && (
        <div className="otp-display">
          <h2>Generated OTP:</h2>
          <p>{otp}</p>
        </div>
      )}

      {error && (
        <div className="error-message">
          {error}
        </div>
      )}
    </div>
  );
}

export default LecturerDashboard;
