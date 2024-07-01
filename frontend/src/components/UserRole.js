import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getUserRoleById, updateUserRole } from '../services/user-role';

const UserRoleList = () => {
  const { id } = useParams();
  const [apiVersion, setApiVersion] = useState(1);

  const [userRole, setUserRole] = useState({});  
  const [validFrom, setValidFrom] = useState('');
  const [validTo, setValidTo] = useState('');

  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      try {
        const fetchedUserRole = await getUserRoleById(id);
        setApiVersion(fetchedUserRole.version);
        setValidFrom(fetchedUserRole.validFrom)
        setValidTo(fetchedUserRole.validTo)

        setUserRole(fetchedUserRole);
      } catch (error) {
        setError(error);
      } finally {
        setIsLoading(false);
      }
    };
    
    fetchData();
  }, [id]);

  const handleUpdateUserRole = async (event) => {
    event.preventDefault();
    const userRoleData = { validFrom, validTo }; 
    try {
      await updateUserRole(id, apiVersion, userRoleData);
    } catch (error) {
      console.error('Error updating user:', error);
    }
  };

  return (
    <div>
      {isLoading && <p>Loading user role...</p>}
      {error && <p>Error fetching user role: {error.message}</p>}
      {!isLoading && !error && (
        <form>
          {id && (
            <>
              <label htmlFor="version">Current version:</label>
              <input type="text" id="apiVersion" value={apiVersion} onChange={(e) => setApiVersion(e.target.value)} />
              <br/>
              <br/>
            </>
          )}
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
                  <td><input type="text" id="name" value={validFrom} onChange={(e) => setValidFrom(e.target.value)} /></td>
                  <td><input type="text" id="name" value={validTo} onChange={(e) => setValidTo(e.target.value)} /></td>
              </tr>
            </tbody>
          </table>
          <button onClick={handleUpdateUserRole} type="submit">Update</button>
        </form>
      )}
    </div>
  );
};

export default UserRoleList;
