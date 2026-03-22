import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { AxiosError } from 'axios';
import { getUnits } from '@/services/unit';
import { Unit } from '@/types';

type ApiError = AxiosError<{ title: string; detail: string }>;

const UnitList = () => {
  const [units, setUnits] = useState<Unit[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<ApiError | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      try {
        const { _embedded: embedded } = await getUnits();
        setUnits(embedded.unitList);
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
      {isLoading && <p>Loading units...</p>}
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

export default UnitList;
