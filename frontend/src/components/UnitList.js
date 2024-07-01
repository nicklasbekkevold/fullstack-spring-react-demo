import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getUnits } from '../services/unit';

const UserList = () => {
  const [units, setUnits] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      try {
        const { _embedded: embedded } = await getUnits();
        setUnits(embedded.unitList);
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
      {isLoading && <p>Loading units...</p>}
      {error && <p>Error fetching units: {error.message}</p>}
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
          {units.map((unit) => (
              <tr key={unit.id}>
                <td>{unit.id}</td>
                <td>{unit.version}</td>
                <td>{unit.name}</td>
                <td>
                  <Link to={`/units/${unit.id}`}>View</Link>
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
