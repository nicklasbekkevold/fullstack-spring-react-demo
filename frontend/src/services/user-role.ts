import axios from 'axios';
import { UserRole } from '@/types';

const baseUrl = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080';
const apiUrl = `${baseUrl}/api/user-roles`;

export const createUserRole = async (userRoleData: Partial<UserRole>): Promise<UserRole> => {
  try {
    const response = await axios.post<UserRole>(apiUrl, userRoleData);
    return response.data;
  } catch (error) {
    console.error('Error creating user role:', error);
    throw error;
  }
};

export const getUserRoles = async (): Promise<{ _embedded: { userRoleList: UserRole[] } }> => {
  try {
    const response = await axios.get<{ _embedded: { userRoleList: UserRole[] } }>(apiUrl);
    return response.data;
  } catch (error) {
    console.error('Error fetching user roles:', error);
    throw error;
  }
};

export const getUserRoleById = async (id: string): Promise<UserRole> => {
  try {
    const response = await axios.get<UserRole>(`${apiUrl}/${id}`);
    return response.data;
  } catch (error) {
    console.error('Error fetching user role:', error);
    throw error;
  }
};

export const getValidUserRoles = async (
  userId: string,
  unitId: string,
  timestamp: string,
): Promise<{ _embedded: { userRoleList: UserRole[] } }> => {
  try {
    const params = {
      userId,
      unitId,
      timestamp: encodeURIComponent(timestamp),
    };
    const response = await axios.get<{ _embedded: { userRoleList: UserRole[] } }>(apiUrl, { params });
    return response.data;
  } catch (error) {
    console.error('Error fetching user role:', error);
    throw error;
  }
};

export const updateUserRole = async (
  id: string,
  apiVersion: number | string,
  userData: { validFrom?: string; validTo?: string },
): Promise<UserRole> => {
  try {
    const response = await axios.put<UserRole>(`${apiUrl}/${id}?version=${apiVersion}`, userData);
    return response.data;
  } catch (error) {
    console.error('Error creating user:', error);
    throw error;
  }
};
