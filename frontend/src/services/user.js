import axios from 'axios';

const baseUrl = process.env.REACT_APP_API_BASE_URL || "http://localhost:8080";
const apiUrl = `${baseUrl}/api/users`

export const createUser = async (userData) => {
  try {
    const response = await axios.post(apiUrl, userData);
    return response.data;
  } catch (error) {
    console.error('Error creating user:', error);
    throw error;
  }
};

export const getUsers = async () => {
  try {
    const response = await axios.get(apiUrl);
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error('Error fetching users:', error);
    throw error;
  }
};

export const getUserById = async (id) => {
  try {
    const response = await axios.get(`${apiUrl}/${id}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching users:', error);
    throw error;
  }
};

export const updateUser = async (id, apiVersion, userData) => {
  try {
    const response = await axios.put(`${apiUrl}/${id}?version=${apiVersion}`, userData);
    return response.data;
  } catch (error) {
    console.error('Error creating user:', error);
    throw error;
  }
};

