import axios from 'axios';

const API = axios.create({ baseURL: '/api/v1' });

export const login = (credentials) => API.post('/auth/login', credentials);
export const getUser = (userId) => API.get(`/users/${userId}/user`);
export const updateUser = (userId, user) => API.put(`/users/${userId}/update`, user);
export const getProducts = () => API.get('/products/all');
// Add other API calls as needed
