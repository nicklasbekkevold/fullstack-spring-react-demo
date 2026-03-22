import axios from 'axios';
import { Role } from '@/types';

const baseUrl = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';
const apiUrl = `${baseUrl}/api/roles`;

export const getRoles = async (): Promise<{ _embedded: { roleList: Role[] } }> => {
  try {
    const response = await axios.get<{ _embedded: { roleList: Role[] } }>(apiUrl);
    return response.data;
  } catch (error) {
    console.error('Error fetching roles:', error);
    throw error;
  }
};

export const getRoleById = async (id: string): Promise<Role> => {
  try {
    const response = await axios.get<Role>(`${apiUrl}/${id}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching roles:', error);
    throw error;
  }
};
