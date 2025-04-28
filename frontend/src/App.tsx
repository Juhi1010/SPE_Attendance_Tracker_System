// import { useState } from 'react'
// import reactLogo from './assets/react.svg'
// import viteLogo from '/vite.svg'
// import './App.css'
// import Login from './co/mponents/login/Login'
// import Register from './components/register/Register'

// function App() {
//   const [count, setCount] = useState(0)

//   return (
//     <>
// <div className="App">
//       {/* <Login /> */}
//       <Register />
//     </div>
      
//     </>
//   )
// }

// export default App

// import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Login from './components/login/Login'
import Register from './components/register/Register'
import LecturerDashboard from './components/lecturer/Lecturer';
import StudentDashboard from './components/student/Student';

const App = () => {
  return (
    <Router>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} /> {/* This is the register route */}
        <Route path="/lecturer" element={<LecturerDashboard />} />
        <Route path="/mark" element={<StudentDashboard />} />
      </Routes>
    </Router>
  );
};

export default App;

