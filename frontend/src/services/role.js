import axios from 'axios';

const baseUrl = process.env.REACT_APP_API_BASE_URL || "http://localhost:8080";
const apiUrl = `${baseUrl}/api/roles`

export const getRoles = async () => {
  try {
    const response = await axios.get(apiUrl);
    return response.data;
  } catch (error) {
    console.error('Error fetching roles:', error);
    throw error;
  }
};

export const getRoleById = async (id) => {
  try {
    const response = await axios.get(`${apiUrl}/${id}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching roles:', error);
    throw error;
  }
};
