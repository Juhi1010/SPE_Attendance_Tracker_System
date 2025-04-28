import React, { useState } from 'react';
import './register.css';

const Register = () => {
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [role, setRole] = useState('STUDENT');
    const [image, setImage] = useState<File | null>(null);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        
        if (!username || !email || !role || !image) {
            setError('Please fill in all fields and upload an image.');
            return;
        }

        const formData = new FormData();
        
        // Create the user object and append to formData
        const userObject = {
            username,
            email,
            role,
            imagePath: image.name // or path if needed
        };

        // Append JSON stringified user object and image file
        formData.append('user', JSON.stringify(userObject));
        formData.append('image', image);

        try {
            const response = await fetch('http://localhost:8082/api/users/register', {
                method: 'POST',
                body: formData,
            });

            const result = await response.json();
            
            if (response.ok) {
                setSuccess(`Registration successful! User ID: ${result.userid}`);
                setError('');
            } else {
                setError(result.message || 'An error occurred during registration.');
                setSuccess('');
            }
        } catch (error) {
            console.error(error);
            setError('An error occurred during registration. Please try again.');
            setSuccess('');
        }
    };

    return (
        <div className="register-container">
            <h2>Register</h2>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="username">Username:</label>
                    <input
                        type="text"
                        id="username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                    />
                </div>
                <div>
                    <label htmlFor="email">Email:</label>
                    <input
                        type="email"
                        id="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </div>
                <div>
                    <label htmlFor="role">Role:</label>
                    <select
                        id="role"
                        value={role}
                        onChange={(e) => setRole(e.target.value)}
                    >
                        <option value="STUDENT">STUDENT</option>
                        <option value="LECTURER">LECTURER</option>
                    </select>
                </div>
                <div>
                    <label htmlFor="image">Image:</label>
                    <input
                        type="file"
                        id="image"
                        onChange={(e) => setImage(e.target.files?.[0] ?? null)}
                    />
                </div>
                <button type="submit">Register</button>
            </form>

            {error && <div className="error">{error}</div>}
            {success && <div className="success">{success}</div>}
        </div>
    );
};

export default Register;
