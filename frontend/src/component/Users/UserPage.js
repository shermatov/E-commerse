// Users
// UserProfile.jsx
import React, { useState, useEffect } from 'react';
import axios from 'axios';

const UserPage = ({ userId }) => {
    const [user, setUser] = useState({});

    useEffect(() => {
        axios.get(`/api/v1/users/${userId}/user`).then(response => setUser(response.data));
    }, [userId]);

    const handleUpdate = async () => {
        try {
            await axios.put(`/api/v1/users/${userId}/update`, user);
            alert('User updated successfully!');
        } catch (error) {
            alert('Failed to update user.');
        }
    };

    return (
        <form>
            <input value={user.name || ''} onChange={(e) => setUser({ ...user, name: e.target.value })} />
            <input value={user.email || ''} onChange={(e) => setUser({ ...user, email: e.target.value })} />
            <button type="button" onClick={handleUpdate}>Update</button>
        </form>
    );
};

export default UserPage;