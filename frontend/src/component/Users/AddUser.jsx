
// AddUser.jsx
import React, { useState } from 'react';
import axios from 'axios';

const AddUser = () => {
    const [newUser, setNewUser] = useState({ name: '', email: '' });

    const handleAddUser = async () => {
        try {
            await axios.post('/api/v1/users/add', newUser);
            alert('User added successfully!');
        } catch (error) {
            alert('Failed to add user.');
        }
    };

    return (
        <form>
            <input placeholder="Name" onChange={(e) => setNewUser({ ...newUser, name: e.target.value })} />
            <input placeholder="Email" onChange={(e) => setNewUser({ ...newUser, email: e.target.value })} />
            <button type="button" onClick={handleAddUser}>Add User</button>
        </form>
    );
};

export default AddUser;
