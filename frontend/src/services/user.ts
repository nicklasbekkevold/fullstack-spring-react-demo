import axios from 'axios';
import { User } from '@/types';

const baseUrl = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';
const apiUrl = `${baseUrl}/api/users`;

export const createUser = async (userData: { name: string }): Promise<User> => {
  try {
    const response = await axios.post<User>(apiUrl, userData);
    return response.data;
  } catch (error) {
    console.error('Error creating user:', error);
    throw error;
  }
};

export const getUsers = async (): Promise<{ _embedded: { userList: User[] } }> => {
  try {
    const response = await axios.get<{ _embedded: { userList: User[] } }>(apiUrl);
    return response.data;
  } catch (error) {
    console.error('Error fetching users:', error);
    throw error;
  }
};

export const getUserById = async (id: string): Promise<User> => {
  try {
    const response = await axios.get<User>(`${apiUrl}/${id}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching users:', error);
    throw error;
  }
};

export const updateUser = async (
  id: string,
  apiVersion: number | string,
  userData: { version: number | string; name: string },
): Promise<User> => {
  try {
    const response = await axios.put<User>(`${apiUrl}/${id}?version=${apiVersion}`, userData);
    return response.data;
  } catch (error) {
    console.error('Error creating user:', error);
    throw error;
  }
};

export const deleteUser = async (id: string, apiVersion: number | string): Promise<void> => {
  try {
    await axios.delete(`${apiUrl}/${id}?version=${apiVersion}`);
  } catch (error) {
    console.error('Error creating user:', error);
    throw error;
  }
};
