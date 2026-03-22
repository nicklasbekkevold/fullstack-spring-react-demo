import axios from 'axios';
import { Unit } from '@/types';

const baseUrl = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';
const apiUrl = `${baseUrl}/api/units`;

export const getUnits = async (): Promise<{ _embedded: { unitList: Unit[] } }> => {
  try {
    const response = await axios.get<{ _embedded: { unitList: Unit[] } }>(apiUrl);
    return response.data;
  } catch (error) {
    console.error('Error fetching units:', error);
    throw error;
  }
};

export const getUnitById = async (id: string): Promise<Unit> => {
  try {
    const response = await axios.get<Unit>(`${apiUrl}/${id}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching units:', error);
    throw error;
  }
};
