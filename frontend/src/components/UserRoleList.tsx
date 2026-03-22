import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { AxiosError } from 'axios';
import { getUserRoles } from '@/services/user-role';
import { UserRole } from '@/types';

type ApiError = AxiosError<{ title: string; detail: string }>;

const UserRoleList = () => {
  const [userRoles, setUserRoles] = useState<UserRole[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<ApiError | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      try {
        const { _embedded: embedded } = await getUserRoles();
        setUserRoles(embedded.userRoleList);
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
      {isLoading && <p>Loading user roles...</p>}
      {error && <p>An error occurred: {error.message}</p>}
      {error?.response?.data && <p>{error.response.data.title}</p>}
      {error?.response?.data && <p>{error.response.data.detail}</p>}
      {!isLoading && !error && (
        <table>
          <thead>
            <tr>
              <th>Id</th>
              <th>Version</th>
              <th>UserId</th>
              <th>UnitId</th>
              <th>RoleId</th>
              <th>ValidFrom</th>
              <th>ValidTo</th>
            </tr>
          </thead>
          <tbody>
            {userRoles.map((userRole) => (
              <tr key={userRole.id}>
                <td>{userRole.id}</td>
                <td>{userRole.version}</td>
                <td>{userRole.user.id}</td>
                <td>{userRole.unit.id}</td>
                <td>{userRole.role.id}</td>
                <td>{userRole.validFrom}</td>
                <td>{userRole.validTo}</td>
                <td>
                  <Link to={`/user-roles/${userRole.id}`}>Edit</Link>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default UserRoleList;
