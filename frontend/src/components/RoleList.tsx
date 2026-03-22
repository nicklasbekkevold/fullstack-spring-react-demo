import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { AxiosError } from 'axios';
import { getRoles } from '@/services/role';
import { Role } from '@/types';

type ApiError = AxiosError<{ title: string; detail: string }>;

const RoleList = () => {
  const [roles, setRoles] = useState<Role[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<ApiError | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      try {
        const { _embedded: embedded } = await getRoles();
        setRoles(embedded.roleList);
      } catch (err) {
        setError(err as ApiError);
      } finally {
        setIsLoading(false);
      }
    };

    fetchData();
  }, []);

  return (
    <div>
      {isLoading && <p>Loading roles...</p>}
      {error && <p>An error occurred: {error.message}</p>}
      {error?.response?.data && <p>{error.response.data.title}</p>}
      {error?.response?.data && <p>{error.response.data.detail}</p>}
      {!isLoading && !error && (
        <table>
          <thead>
            <tr>
              <th>Id</th>
              <th>Version</th>
              <th>Name</th>
            </tr>
          </thead>
          <tbody>
            {roles.map((role) => (
              <tr key={role.id}>
                <td>{role.id}</td>
                <td>{role.version}</td>
                <td>{role.name}</td>
                <td>
                  <Link to={`/roles/${role.id}`}>View</Link>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default RoleList;
