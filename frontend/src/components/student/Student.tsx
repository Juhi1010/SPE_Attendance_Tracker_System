// import React, { useState } from 'react';
// import axios from 'axios';

// const Student = () => {
//   const [otp, setOtp] = useState('');
//   const [image, setImage] = useState<File | null>(null); // Type image as File or null
//   const [errorMessage, setErrorMessage] = useState('');
//   const [successMessage, setSuccessMessage] = useState('');

//   const handleOtpChange = (e: React.ChangeEvent<HTMLInputElement>) => {
//     setOtp(e.target.value);
//   };

//   const handleImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
//     if (e.target.files) {
//       setImage(e.target.files[0]); // Now this works since image is typed as File | null
//     }
//   };

//   const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
//     event.preventDefault();
//     const token = 'Bearer your_jwt_token'; // Replace with actual token logic

//     if (!image) {
//       setErrorMessage('Please upload a selfie.');
//       return;
//     }

//     const formData = new FormData();
//     formData.append('otp', otp);
//     formData.append('image', image);

//     try {
//       const response = await axios.post(
//         'http://localhost:8080/scan', // Replace with the actual endpoint URL
//         formData,
//         {
//           headers: {
//             'Authorization': token,
//             'Content-Type': 'multipart/form-data',
//           },
//         }
//       );
//       setSuccessMessage('Attendance marked successfully!');
//     } catch (error) {
//       setErrorMessage('Failed to mark attendance.');
//     }
//   };

//   return (
//     <div>
//       <h2>Mark Attendance</h2>
//       {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
//       {successMessage && <p style={{ color: 'green' }}>{successMessage}</p>}
//       <form onSubmit={handleSubmit}>
//         <div>
//           <label>Enter OTP:</label>
//           <input
//             type="text"
//             value={otp}
//             onChange={handleOtpChange}
//             required
//           />
//         </div>
//         <div>
//           <label>Take Selfie:</label>
//           <input
//             type="file"
//             onChange={handleImageChange}
//             accept="image/*"
//             required
//           />
//         </div>
//         <button type="submit">Submit</button>
//       </form>
//     </div>
//   );
// };

// export default Student;



// import React, { useState, useRef } from 'react';
// import axios from 'axios';

// const Student = () => {
//   const [otp, setOtp] = useState('');
//   const [image, setImage] = useState<File | null>(null); // To store the captured image
//   const [errorMessage, setErrorMessage] = useState('');
//   const [successMessage, setSuccessMessage] = useState('');
//   const videoRef = useRef<HTMLVideoElement>(null); // Ref to control video element
//   const canvasRef = useRef<HTMLCanvasElement>(null); // Ref to draw captured image on canvas

//   // Start the webcam when the component mounts
//   const startCamera = () => {
//     if (videoRef.current) {
//       navigator.mediaDevices.getUserMedia({ video: true })
//         .then((stream) => {
//           videoRef.current!.srcObject = stream; // Add the null check here
//         })
//         .catch((err) => {
//           console.error("Error accessing webcam: ", err);
//         });
//     }
//   };

//   // Capture a selfie when the button is clicked
//   const captureSelfie = () => {
//     if (canvasRef.current && videoRef.current) {
//       const context = canvasRef.current.getContext('2d');
//       if (context) {
//         // Draw the current video frame to the canvas
//         context.drawImage(videoRef.current, 0, 0, canvasRef.current.width, canvasRef.current.height);
//         const dataUrl = canvasRef.current.toDataURL('image/jpeg'); // Convert canvas to image URL
//         const imageFile = dataURLtoFile(dataUrl, 'selfie.jpg'); // Convert dataURL to file
//         setImage(imageFile); // Store the captured image as a File
//       }
//     }
//   };

//   // Convert data URL to File object
//   const dataURLtoFile = (dataUrl: string, filename: string) => {
//     const arr = dataUrl.split(',');
//     const match = arr[0].match(/:(.*?);/); // Attempt to match the MIME type
  
//     if (!match) {
//       throw new Error('Invalid data URL: MIME type could not be determined.');
//     }
  
//     const mime = match[1]; // Extract the MIME type
//     const bstr = atob(arr[1]);
//     let n = bstr.length, u8arr = new Uint8Array(n);
//     while (n--) {
//       u8arr[n] = bstr.charCodeAt(n);
//     }
//     return new File([u8arr], filename, { type: mime });
//   };

//   const handleOtpChange = (e: React.ChangeEvent<HTMLInputElement>) => {
//     setOtp(e.target.value);
//   };

//   const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
//     event.preventDefault();
//     const token = 'Bearer your_jwt_token'; // Replace with actual token logic

//     if (!image) {
//       setErrorMessage('Please take a selfie.');
//       return;
//     }

//     const formData = new FormData();
//     formData.append('otp', otp);
//     formData.append('image', image);

//     try {
//       const response = await axios.post(
//         'http://localhost:8080/scan', // Replace with the actual endpoint URL
//         formData,
//         {
//           headers: {
//             'Authorization': token,
//             'Content-Type': 'multipart/form-data',
//           },
//         }
//       );
//       setSuccessMessage('Attendance marked successfully!');
//     } catch (error) {
//       setErrorMessage('Failed to mark attendance.');
//     }
//   };

//   return (
//     <div>
//       <h2>Mark Attendance</h2>
//       {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
//       {successMessage && <p style={{ color: 'green' }}>{successMessage}</p>}

//       {/* Video Element for Live Feed */}
//       <div>
//         <video ref={videoRef} autoPlay width="320" height="240" />
//         <canvas ref={canvasRef} style={{ display: 'none' }} width="320" height="240" />
//       </div>
//       <button onClick={startCamera}>Start Camera</button>
//       <button onClick={captureSelfie}>Take Selfie</button>

//       <form onSubmit={handleSubmit}>
//         <div>
//           <label>Enter OTP:</label>
//           <input
//             type="text"
//             value={otp}
//             onChange={handleOtpChange}
//             required
//           />
//         </div>
//         <button type="submit">Submit</button>
//       </form>
//     </div>
//   );
// };

// export default Student;


// import React, { useState, useRef, useEffect } from 'react';
// import axios from 'axios';

// const MarkAttendance = () => {
//   const [otp, setOtp] = useState('');
//   const [image, setImage] = useState<File | null>(null);
//   const [errorMessage, setErrorMessage] = useState('');
//   const [successMessage, setSuccessMessage] = useState('');
//   const [isSelfieTaken, setIsSelfieTaken] = useState(false); // To toggle between video and selfie view
//   const videoRef = useRef<HTMLVideoElement>(null); // Ref to control video element
//   const canvasRef = useRef<HTMLCanvasElement>(null); // Ref to draw captured image on canvas

//   // Start the webcam when the component mounts
//   useEffect(() => {
//     startCamera();
//   }, []);

//   const startCamera = () => {
//     if (videoRef.current) {
//       navigator.mediaDevices.getUserMedia({ video: true })
//         .then((stream) => {
//           videoRef.current!.srcObject = stream; // Add the null check here
//         })
//         .catch((err) => {
//           console.error("Error accessing webcam: ", err);
//         });
//     }
//   };

//   // Capture a selfie when the button is clicked
//   const captureSelfie = () => {
//     if (canvasRef.current && videoRef.current) {
//       const context = canvasRef.current.getContext('2d');
//       if (context) {
//         // Draw the current video frame to the canvas
//         context.drawImage(videoRef.current, 0, 0, canvasRef.current.width, canvasRef.current.height);
//         const dataUrl = canvasRef.current.toDataURL('image/jpeg'); // Convert canvas to image URL
//         const imageFile = dataURLtoFile(dataUrl, 'selfie.jpg'); // Convert dataURL to file
//         setImage(imageFile); // Store the captured image as a File
//         setIsSelfieTaken(true); // Toggle to show selfie
//       }
//     } else {
//       setErrorMessage('Failed to capture selfie. Ensure webcam is working.');
//     }
//   };

//   // Convert data URL to File object
//   const dataURLtoFile = (dataUrl: string, filename: string) => {
//     const arr = dataUrl.split(',');
//     const match = arr[0].match(/:(.*?);/); // Attempt to match the MIME type

//     if (!match) {
//       throw new Error('Invalid data URL: MIME type could not be determined.');
//     }

//     const mime = match[1]; // Extract the MIME type
//     const bstr = atob(arr[1]);
//     let n = bstr.length, u8arr = new Uint8Array(n);
//     while (n--) {
//       u8arr[n] = bstr.charCodeAt(n);
//     }
//     return new File([u8arr], filename, { type: mime });
//   };

//   const handleOtpChange = (e: React.ChangeEvent<HTMLInputElement>) => {
//     setOtp(e.target.value);
//   };

//   const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
//     event.preventDefault();
//     const token = 'Bearer your_jwt_token'; // Replace with actual token logic

//     if (!image) {
//       setErrorMessage('Please take a selfie.');
//       return;
//     }

//     const formData = new FormData();
//     formData.append('otp', otp);
//     formData.append('image', image);

//     try {
//       const response = await axios.post(
//         'http://localhost:8080/scan', // Replace with the actual endpoint URL
//         formData,
//         {
//           headers: {
//             'Authorization': token,
//             'Content-Type': 'multipart/form-data',
//           },
//         }
//       );
//       setSuccessMessage('Attendance marked successfully!');
//     } catch (error) {
//       setErrorMessage('Failed to mark attendance.');
//     }
//   };

//   return (
//     <div>
//       <h2>Mark Attendance</h2>
//       {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
//       {successMessage && <p style={{ color: 'green' }}>{successMessage}</p>}

//       {/* Video Element for Live Feed */}
//       <div>
//       {isSelfieTaken && image && (
//         <div>
//             <p>Your Selfie:</p>
//             <img src={URL.createObjectURL(image)} alt="Captured Selfie" width="320" height="240" />
//         </div>
//         )}

//         <canvas ref={canvasRef} style={{ display: 'none' }} width="320" height="240" />
//       </div>

//       {/* Button to Capture Selfie */}
//       {!isSelfieTaken ? (
//         <button onClick={captureSelfie}>Take Selfie</button>
//       ) : (
//         <button onClick={() => setIsSelfieTaken(false)}>Retake Selfie</button>
//       )}

//       {/* Form to submit OTP and selfie */}
//       <form onSubmit={handleSubmit}>
//         <div>
//           <label>Enter OTP:</label>
//           <input
//             type="text"
//             value={otp}
//             onChange={handleOtpChange}
//             required
//           />
//         </div>
//         <button type="submit">Submit</button>
//       </form>
//     </div>
//   );
// };

// export default MarkAttendance;


import React, { useState, useRef, useEffect } from 'react';
import axios from 'axios';
import './student.css'

const MarkAttendance = () => {
  const [otp, setOtp] = useState('');
  const [studentId, setStudentId] = useState('');
  const [image, setImage] = useState<File | null>(null);
  const [errorMessage, setErrorMessage] = useState('');
  const [successMessage, setSuccessMessage] = useState('');
  const [isSelfieTaken, setIsSelfieTaken] = useState(false); // To toggle between video and selfie view
  const videoRef = useRef<HTMLVideoElement>(null); // Ref to control video element
  const canvasRef = useRef<HTMLCanvasElement>(null); // Ref to draw captured image on canvas
  

  // Start the webcam when the component mounts
  useEffect(() => {
    startCamera();
  }, []);

    const startCamera = () => {
    if (videoRef.current) {
      navigator.mediaDevices.getUserMedia({ video: true })
        .then((stream) => {
          videoRef.current!.srcObject = stream; // Add the null check here
        })
        .catch((err) => {
          console.error("Error accessing webcam: ", err);
        });
    }
  };

  // Capture a selfie when the button is clicked
  const captureSelfie = () => {
    if (canvasRef.current && videoRef.current) {
      const context = canvasRef.current.getContext('2d');
      if (context) {
        // Draw the current video frame to the canvas
        context.drawImage(videoRef.current, 0, 0, canvasRef.current.width, canvasRef.current.height);
        const dataUrl = canvasRef.current.toDataURL('image/jpeg'); // Convert canvas to image URL
        const imageFile = dataURLtoFile(dataUrl, 'selfie.jpg'); // Convert dataURL to file
        setImage(imageFile); // Store the captured image as a File
        setIsSelfieTaken(true); // Toggle to show selfie
      }
    } else {
      setErrorMessage('Failed to capture selfie. Ensure webcam is working.');
      console.error("Failed to capture selfie: No canvas or video element found.");
    }
  };

  // Convert data URL to File object
  const dataURLtoFile = (dataUrl: string, filename: string) => {
    const arr = dataUrl.split(',');
    const match = arr[0].match(/:(.*?);/); // Attempt to match the MIME type

    if (!match) {
      throw new Error('Invalid data URL: MIME type could not be determined.');
    }

    const mime = match[1]; // Extract the MIME type
    const bstr = atob(arr[1]);
    let n = bstr.length, u8arr = new Uint8Array(n);
    while (n--) {
      u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr], filename, { type: mime });
  };

  const handleOtpChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setOtp(e.target.value);
  };

  const printFormData = (formData: FormData) => {
    formData.forEach((value, key) => {
      console.log(key, value);
    });
  };

  const handleStudentIdChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setStudentId(e.target.value);
  };

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    const token = localStorage.getItem('token'); 

       if (!token) {
            setErrorMessage('Unauthorized. Please login first.');
            return;
        } 

    if (!image) {
      setErrorMessage('Please take a selfie.');
      return;
    }

    if (!studentId) {
        setErrorMessage('Please enter your Student ID.');
        return;
    }
      
    if (!otp) {
        setErrorMessage('Please enter the OTP.');
        return;
    }

    const formData = new FormData();

    const qrCodeRequest = {
      studentId: studentId,
      qrData: otp, // Treat entered OTP as qrData
    };

    // formData.append('data', new Blob([JSON.stringify(qrCodeRequest)], { type: 'application/json' }));
    formData.append('data', JSON.stringify(qrCodeRequest));
    formData.append('image', image);


    printFormData(formData);


    try {
      const apiBase = import.meta.env.VITE_API_BASE_URL;
      const response = await axios.post(
          `${apiBase}/api/users/scan`, // Replace with the actual endpoint URL
        formData,
        {
          headers: {
            'Authorization': `Bearer ${token}`,
            "Content-Type": "multipart/form-data",
          },
        }
      );

      console.log('Response:', response.data);

      if (response.data === 'Attendance marked successfully!') {
        setSuccessMessage('Attendance marked successfully!');
        setErrorMessage('');  // Clear previous error if any
      } else {
        setErrorMessage(response.data); // Face verification failed etc.
        setSuccessMessage('');
      }
    
    } catch (error: any) {
      console.error('Error:', error);
    
      if (error.response) {
        // If backend sends error response
        setErrorMessage(error.response.data || 'Something went wrong.');
      } else {
        setErrorMessage('Failed to mark attendance. Server not reachable.');
      }
      setSuccessMessage('');
    }
      

    //   setSuccessMessage('Attendance marked successfully!');
    // } catch (error) {
    //   setErrorMessage('Failed to mark attendance.');
    // }
  };

  return (
    <div>
      <h2>Mark Attendance</h2>
      {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
      {successMessage && <p style={{ color: 'green' }}>{successMessage}</p>}

      {/* Video Element for Live Feed */}
      <div>
        {!isSelfieTaken ? (
          <video ref={videoRef} autoPlay width="320" height="240" />
        ) : (
          <div>
            <p>Your Selfie:</p>
            {image && (
              <img src={URL.createObjectURL(image)} alt="Captured Selfie" width="320" height="240" />
            )}
          </div>
        )}
        <canvas ref={canvasRef} style={{ display: 'none' }} width="320" height="240" />
      </div>

      {/* Button to Capture Selfie */}
      {!isSelfieTaken ? (
        <button onClick={captureSelfie}>Take Selfie</button>
      ) : (
        <button onClick={() => setIsSelfieTaken(false)}>Retake Selfie</button>
      )}

      {/* Form to submit OTP and selfie */}
      <form onSubmit={handleSubmit}>
      <div>
          <label>Enter Student ID:</label>
          <input
            type="text"
            value={studentId}
            onChange={handleStudentIdChange}
            required
          />
        </div>
        <div>
          <label>Enter OTP:</label>
          <input
            type="text"
            value={otp}
            onChange={handleOtpChange}
            required
          />
        </div>
        <button type="submit">Submit</button>
      </form>
    </div>
  );
};

export default MarkAttendance;




