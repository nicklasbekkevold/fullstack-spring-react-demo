import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { AxiosError } from 'axios';
import { getRoleById } from '@/services/role';
import { Role } from '@/types';

type ApiError = AxiosError<{ title: string; detail: string }>;

const RoleView = () => {
  const { id } = useParams<{ id: string }>();
  const [role, setRole] = useState<Partial<Role>>({});
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<ApiError | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      try {
        const fetchedRole = await getRoleById(id!);
        setRole(fetchedRole);
      } catch (err) {
        setError(err as ApiError);
      } finally {
        setIsLoading(false);
      }
    };

    fetchData();
  }, [id]);

  return (
    <div>
      {isLoading && <p>Loading role...</p>}
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
            <tr key={role.id}>
              <td>{role.id}</td>
              <td>{role.version}</td>
              <td>{role.name}</td>
            </tr>
          </tbody>
        </table>
      )}
    </div>
  );
};

export default RoleView;
