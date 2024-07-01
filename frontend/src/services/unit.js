import axios from 'axios';

const baseUrl = process.env.REACT_APP_API_BASE_URL || "http://localhost:8080";
const apiUrl = `${baseUrl}/api/units`

export const getUnits = async () => {
  try {
    const response = await axios.get(apiUrl);
    return response.data;
  } catch (error) {
    console.error('Error fetching units:', error);
    throw error;
  }
};

export const getUnitById = async (id) => {
  try {
    const response = await axios.get(`${apiUrl}/${id}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching units:', error);
    throw error;
  }
};
