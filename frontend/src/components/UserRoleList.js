import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getUserRoles } from '../services/user-role';

const UserList = () => {
  const [userRoles, setUserRoles] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      try {
        const { _embedded: embedded } = await getUserRoles();
        setUserRoles(embedded.userRoleList);
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
      {isLoading && <p>Loading user roles...</p>}
      {error && <p>Error fetching user roles: {error.message}</p>}
      {!isLoading && !error && (
        <table>
          <thead>
            <tr>
              <th>Id</th>
              <th>Version</th>
              <th>UserId</th>
              <th>UnitId</th>
              <th>ValidFrom</th>
              <th>ValidTo</th>
            </tr>
          </thead>
          <tbody>
          {userRoles.map((userRole) => (
              <tr key={userRole.id}>
                <td>{userRole.version}</td>
                <td>{userRole.user.id}</td>
                <td>{userRole.unit.id}</td>
                <td>{userRole.role.id}</td>
                <td>{userRole.validFrom}</td>
                <td>{userRole.validTo}</td>
                <td>
                  <Link to={`/user-roles/${userRole.id}`}>View</Link>
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
