import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getRoles } from '../services/role';

const UserList = () => {
  const [roles, setRoles] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      try {
        const { _embedded: embedded } = await getRoles();
        setRoles(embedded.roleList);
      } catch (error) {
        setError(error);
      } finally {
        setIsLoading(false);
      }
    };

    fetchData();
  }, []);

  return (
    <div>
      {isLoading && <p>Loading roles...</p>}
      {error && <p>Error fetching roles: {error.message}</p>}
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

export default UserList;
