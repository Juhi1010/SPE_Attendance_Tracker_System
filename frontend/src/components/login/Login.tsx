// import React, { useState } from 'react';
// import { useNavigate } from 'react-router-dom'; 
// import './Login.css';

// const Login: React.FC = () => {
//   const [email, setEmail] = useState('');
//   const [password, setPassword] = useState('');
//   const [error, setError] = useState('');
//   const [loading, setLoading] = useState(false);
//   const navigate = useNavigate();

//   const handleSubmit = async (e: React.FormEvent) => {
//     e.preventDefault();

//     if (!email || !password) {
//       setError('Please enter both email and password.');
//       return;
//     }

//     setError('');
//     setLoading(true);

//     try {
//       const response = await fetch('http://localhost:8082/api/users/login', {
//         method: 'POST',
//         headers: {
//           'Content-Type': 'application/json',
//         },
//         body: JSON.stringify({ email, password }),
//       });

//       const data = await response.json();

//       if (!response.ok) {
//         setError(data.message || 'Login failed.');
//       } else {
//         localStorage.setItem('token', data.token);
//         alert('Login successful!');
//         // Optionally navigate to another page
//       }
//     } catch (err) {
//       setError('Error occurred during login.');
//     } finally {
//       setLoading(false);
//     }
//   };

//   return (
//     <div className="login-container">
//       <h2>Login</h2>
//       {error && <p className="error">{error}</p>}
//       <form onSubmit={handleSubmit}>
//         <div className="input-group">
//           <label>Email</label>
//           <input
//             type="email"
//             value={email}
//             onChange={e => setEmail(e.target.value)}
//             placeholder="Enter your email"
//           />
//         </div>
//         <div className="input-group">
//           <label>Password</label>
//           <input
//             type="password"
//             value={password}
//             onChange={e => setPassword(e.target.value)}
//             placeholder="Enter your password"
//           />
//         </div>
//         <button type="submit" disabled={loading}>
//           {loading ? 'Logging in...' : 'Login'}
//         </button>
//       </form>

//        <div className="register-link">
//          <p>
//           Don't have an account? 
//           <span 
//             style={{ color: '#4CAF50', cursor: 'pointer' }} 
//             onClick={() => navigate('/register')} // Use navigate to go to register page
//           >
//             register
//           </span>
//         </p>
//       </div>
      
//     </div>
//   );
// };

// export default Login;


import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './login.css';

const Login: React.FC = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  const decodeJWT = (token: string) => {
    try {
      const payload = token.split('.')[1];
      const decodedPayload = JSON.parse(atob(payload));
      return decodedPayload;
    } catch (error) {
      console.error('Failed to decode JWT:', error);
      return null;
    }
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!email || !password) {
      setError('Please enter both email and password.');
      return;
    }

    setError('');
    setLoading(true);

    try {
      const apiBase = import.meta.env.VITE_API_BASE_URL;
      const response = await fetch(`${apiBase}/api/users/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ email, password }),
      });

      const data = await response.json();

      if (!response.ok) {
        setError(data.message || 'Login failed.');
      } else {
        const token = data.token;
        localStorage.setItem('token', token);

        const decoded = decodeJWT(token);

        if (decoded && decoded.role) {
          const role = decoded.role;
          localStorage.setItem('role', role); // optionally save role too

          alert('Login successful!');

          if (role === 'LECTURER') {
            navigate('/lecturer');
          } else if (role === 'STUDENT') {
            navigate('/mark');
          } else {
            setError('Unknown role. Cannot redirect.');
          }
        } else {
          setError('Invalid token. Role not found.');
        }
      }
    } catch (err) {
      setError('Error occurred during login.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="login-container">
      <h2>Login</h2>
      {error && <p className="error">{error}</p>}
      <form onSubmit={handleSubmit}>
        <div className="input-group">
          <label>Email</label>
          <input
            type="email"
            value={email}
            onChange={e => setEmail(e.target.value)}
            placeholder="Enter your email"
          />
        </div>
        <div className="input-group">
          <label>Password</label>
          <input
            type="password"
            value={password}
            onChange={e => setPassword(e.target.value)}
            placeholder="Enter your password"
          />
        </div>
        <button type="submit" disabled={loading}>
          {loading ? 'Logging in...' : 'Login'}
        </button>
      </form>

      <div className="register-link">
        <p>
          Don't have an account?{' '}
          <span
            style={{ color: '#4CAF50', cursor: 'pointer' }}
            onClick={() => navigate('/register')}
          >
            register
          </span>
        </p>
      </div>
    </div>
  );
};

export default Login;




