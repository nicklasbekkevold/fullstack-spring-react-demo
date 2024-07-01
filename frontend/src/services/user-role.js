import axios from 'axios';

const baseUrl = process.env.REACT_APP_API_BASE_URL || "http://localhost:8080";
const apiUrl = `${baseUrl}/api/user-roles`

export const getUserRoles = async () => {
  try {
    const response = await axios.get(apiUrl);
    return response.data;
  } catch (error) {
    console.error('Error fetching user roles:', error);
    throw error;
  }
};

export const getUserRoleById = async (id) => {
  try {
    const response = await axios.get(`${apiUrl}/${id}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching user role:', error);
    throw error;
  }
};

export const updateUserRole = async (id, apiVersion, userData) => {
  try {
    const response = await axios.put(`${apiUrl}/${id}?version=${apiVersion}`, userData);
    return response.data;
  } catch (error) {
    console.error('Error creating user:', error);
    throw error;
  }
};