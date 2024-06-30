import axios from 'axios';

const baseUrl = 'http://localhost:8080/users';

export const createUser = async (userData) => {
  try {
    const response = await axios.post(baseUrl, userData);
    return response.data;
  } catch (error) {
    console.error('Error creating user:', error);
    throw error; // Re-throw for handling in components
  }
};