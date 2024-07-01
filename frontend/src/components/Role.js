import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getRoleById } from '../services/role';

const RoleList = () => {
  const { id } = useParams();
  const [role, setRole] = useState({});
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      try {
        const fetchedRole = await getRoleById(id);
        setRole(fetchedRole);
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

export default RoleList;
