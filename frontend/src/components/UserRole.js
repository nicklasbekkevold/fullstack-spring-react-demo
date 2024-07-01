import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getUserRoleById } from '../services/user-role';

const UserRoleList = () => {
  const { id } = useParams();
  const [userRole, setUserRole] = useState({});
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      try {
        const fetchedUserRole = await getUserRoleById(id);
        setUserRole(fetchedUserRole);
      } catch (error) {
        setError(error);
      } finally {
        setIsLoading(false);
      }
    };
    
    fetchData();
  }, [id]);

  return (
    <div>
      {isLoading && <p>Loading user role...</p>}
      {error && <p>Error fetching user role: {error.message}</p>}
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
            <tr key={userRole.id}>
                <td>{userRole.version}</td>
                <td>{userRole.user?.id}</td>
                <td>{userRole.unit?.id}</td>
                <td>{userRole.role?.id}</td>
                <td>{userRole.validFrom}</td>
                <td>{userRole.validTo}</td>
            </tr>
          </tbody>
        </table>
      )}
    </div>
  );
};

export default UserRoleList;
