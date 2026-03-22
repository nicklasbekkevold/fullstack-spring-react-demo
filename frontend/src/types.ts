export interface Role {
  id: number;
  version: number;
  name: string;
}

export interface Unit {
  id: number;
  version: number;
  name: string;
}

export interface User {
  id: number;
  version: number;
  name: string;
}

export interface UserRole {
  id: number;
  version: number;
  user: { id: number };
  unit: { id: number };
  role: { id: number };
  validFrom: string;
  validTo: string;
  userId?: string;
  unitId?: string;
  roleId?: string;
}
