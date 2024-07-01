import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { createUserRole, getUserRoleById, updateUserRole } from '../services/user-role';

const UserRoleForm = () => {
  const { id } = useParams();
  const [apiVersion, setApiVersion] = useState(1);
  const [userRole, setUserRole] = useState({});  

  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    console.log(id);
    if (id) {
      const fetchData = async () => {
        setIsLoading(true);
        try {
          const fetchedUserRole = await getUserRoleById(id);
          setApiVersion(fetchedUserRole.version);
          
          setUserRole(fetchedUserRole);
        } catch (error) {
          setError(error);
        } finally {
          setIsLoading(false);
        }
      };
      fetchData();
    }
  }, [id]);

  const handleCreateUserRole = async (event) => {
    event.preventDefault();
    try {
      await createUserRole(userRole);
      setApiVersion('');
    } catch (error) {
      setError(error);
      console.error('Error creating user:', error);
    }
  };

  const handleUpdateUserRole = async (event) => {
    event.preventDefault();
    const userRoleData = { validFrom: userRole.validFrom, validTo: userRole.validTo }; 
    try {
      await updateUserRole(id, apiVersion, userRoleData);
    } catch (error) {
      setError(error);
      console.error('Error updating user:', error);
    }
  };

  return (
    <div>
      {isLoading && <p>Loading user role...</p>}
      {error && <p>An error occurred: {error.message}</p>}
      {error?.response?.data && <p>{error.response.data.title}</p>}
      {error?.response?.data && <p>{error.response.data.detail}</p>}
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
                {id && (<th>Id</th>)}
                {id && (<th>Version</th>)}
                <th>UserId</th>
                <th>UnitId</th>
                <th>RoleId</th>
                <th>ValidFrom</th>
                <th>ValidTo</th>
              </tr>
            </thead>
            <tbody>
              <tr key={userRole.id}>
                  {id && (<td>{userRole.id}</td>)}
                  {id && (<td>{userRole.version}</td>)}
                  <td>{id ? userRole.user?.id : <input type="text" id="userId" defaultValue={userRole.userId} onChange={(e) => setUserRole({...userRole, userId: e.target.value})} />}</td>
                  <td>{id ? userRole.unit?.id : <input type="text" id="unitId" defaultValue={userRole.unitId} onChange={(e) => setUserRole({...userRole, unitId: e.target.value})} />}</td>
                  <td>{id ? userRole.role?.id : <input type="text" id="roleId" defaultValue={userRole.roleId} onChange={(e) => setUserRole({...userRole, roleId: e.target.value})} />}</td>
                  <td><input type="text" id="validFrom" defaultValue={userRole.validFrom} onChange={(e) => setUserRole({...userRole, validFrom: e.target.value})} /></td>
                  <td><input type="text" id="validTo" defaultValue={userRole.validTo} onChange={(e) => setUserRole({...userRole, validTo: e.target.value})} /></td>
              </tr>
            </tbody>
          </table>
          { id ? (
            <button onClick={handleUpdateUserRole} type="submit">Update</button>
          ) : (
            <button onClick={handleCreateUserRole} type="submit">Create</button>
          )}
        </form>
      )}
    </div>
  );
};

export default UserRoleForm;
