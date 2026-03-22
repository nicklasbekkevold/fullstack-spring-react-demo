import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { AxiosError } from 'axios';
import { getUnitById } from '@/services/unit';
import { Unit } from '@/types';

type ApiError = AxiosError<{ title: string; detail: string }>;

const UnitView = () => {
  const { id } = useParams<{ id: string }>();
  const [unit, setUnit] = useState<Partial<Unit>>({});
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<ApiError | null>(null);

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      try {
        const fetchedUnit = await getUnitById(id!);
        setUnit(fetchedUnit);
      } catch (err) {
        setError(err as ApiError);
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

export default UnitView;
