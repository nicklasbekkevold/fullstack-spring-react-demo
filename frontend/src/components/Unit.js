import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getUnitById } from '../services/unit';

const UnitList = () => {
  const { id } = useParams();
  const [unit, setUnit] = useState({});
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      try {
        const fetchedUnit = await getUnitById(id);
        setUnit(fetchedUnit);
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
      {isLoading && <p>Loading unit...</p>}
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
            <tr key={unit.id}>
              <td>{unit.id}</td>
              <td>{unit.version}</td>
              <td>{unit.name}</td>
            </tr>
          </tbody>
        </table>
      )}
    </div>
  );
};

export default UnitList;
