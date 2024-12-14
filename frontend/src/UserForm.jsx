import React, { useState, useEffect } from 'react';
import axios from 'axios';


const UserForm = () => {
    const [users, setUsers] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchUsers = async () => {
            try {
                const response = await axios.get('/api/v1/users/all');
                setUsers(response.data);
            } catch (err) {
                console.error('Error fetching users:', err); // Log the error
                setError('Failed to fetch users');
            }
        };

        fetchUsers();
    }, []);

    return (
        <div>
            {error && <div>{error}</div>}
            <ul>
                {users.map((user) => (
                    <li key={user.id}>{user.name}</li>
                ))}
            </ul>
        </div>
    );
};

export default UserForm;
