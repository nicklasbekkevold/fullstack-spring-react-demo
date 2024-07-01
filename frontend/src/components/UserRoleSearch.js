import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { getValidUserRoles } from '../services/user-role';

const UserRoleList = () => {
  const [userId, setUserId] = useState('');
  const [unitId, setUnitId] = useState('');
  const [timestamp, setTimestamp] = useState(new Date().toISOString().split('.')[0]+"Z");
  const [userRoles, setUserRoles] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const handleSearch = async () => {
    setIsLoading(true);
    setError(null);

    try {
      const response = await getValidUserRoles(userId, unitId, timestamp);
      setUserRoles(response._embedded.userRoleList);
    } catch (error) {
      setError(error);
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div>
      <form onSubmit={(e) => e.preventDefault()}>
        <label htmlFor="userId">User ID:</label>
        <input type="text" id="userId" value={userId} onChange={(e) => setUserId(e.target.value)} />

        <label htmlFor="unitId">Unit ID:</label>
        <input type="text" id="unitId" value={unitId} onChange={(e) => setUnitId(e.target.value)} />

        <label htmlFor="timestamp">Timestamp:</label>
        <input type="text" id="timestamp" value={timestamp} onChange={(e) => setTimestamp(e.target.value)}/>

        <button type="submit" disabled={isLoading} onClick={handleSearch}>
          {isLoading ? 'Searching...' : 'Search'}
        </button>
      </form>

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
