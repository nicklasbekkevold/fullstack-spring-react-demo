import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { AxiosError } from 'axios';
import { getUsers } from '@/services/user';
import { User } from '@/types';

type ApiError = AxiosError<{ title: string; detail: string }>;

const UserList = () => {
  const [users, setUsers] = useState<User[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<ApiError | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      try {
        const { _embedded: embedded } = await getUsers();
        setUsers(embedded.userList);
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
      {isLoading && <p>Loading users...</p>}
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
            {users.map((user) => (
              <tr key={user.id}>
                <td>{user.id}</td>
                <td>{user.version}</td>
                <td>{user.name}</td>
                <td>
                  <Link to={`/users/${user.id}`}>Edit</Link>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      )}
    </div>
  );
};

export default UserList;
