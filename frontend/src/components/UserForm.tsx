import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { AxiosError } from 'axios';
import { createUser, getUserById, updateUser, deleteUser } from '@/services/user';

type ApiError = AxiosError<{ title: string; detail: string }>;

const UserForm = () => {
  const { id } = useParams<{ id: string }>();
  const [apiVersion, setApiVersion] = useState<number | string>(1);
  const [version, setVersion] = useState<number | string>(1);
  const [name, setName] = useState('');

  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<ApiError | null>(null);

  useEffect(() => {
    if (id) {
      const fetchData = async () => {
        setIsLoading(true);
        try {
          const fetchedUser = await getUserById(id);
          setApiVersion(fetchedUser.version);
          setVersion(fetchedUser.version);
          setName(fetchedUser.name);
        } catch (err) {
          setError(err as ApiError);
        } finally {
          setIsLoading(false);
        }
      };
      fetchData();
    }
  }, [id]);

  const handleCreateUser = async (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();
    const userData = { name };
    try {
      await createUser(userData);
      setName('');
      setVersion('');
      setApiVersion('');
    } catch (err) {
      setError(err as ApiError);
      console.error('Error creating user:', err);
    }
  };

  const handleUpdateUser = async (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();
    const userData = { version, name };
    try {
      await updateUser(id!, apiVersion, userData);
    } catch (err) {
      console.log(err);
      setError(err as ApiError);
      console.error('Error updating user:', err);
    }
  };

  const handleDeleteUser = async (event: React.MouseEvent<HTMLButtonElement>) => {
    event.preventDefault();
    try {
      await deleteUser(id!, apiVersion);
      setName('');
      setVersion('');
      setApiVersion('');
    } catch (err) {
      setError(err as ApiError);
      console.error('Error deleting user:', err);
    }
  };

  return (
    <div>
      {isLoading && <p>Loading user...</p>}
      {error && <p>An error occurred: {error.message}</p>}
      {error?.response?.data && <p>{error.response.data.title}</p>}
      {error?.response?.data && <p>{error.response.data.detail}</p>}
      {!isLoading && !error && (
        <form>
          {id && (
            <>
              <label htmlFor="version">Current version:</label>
              <input type="text" id="apiVersion" value={apiVersion} onChange={(e) => setApiVersion(e.target.value)} />
              <br />
              <br />
            </>
          )}
          <table>
            <thead>
              <tr>
                <th>Id</th>
                <th>Version</th>
                <th>Name</th>
              </tr>
            </thead>
            <tbody>
              <tr key={id}>
                <td>{id}</td>
                <td><input type="text" id="version" value={version} onChange={(e) => setVersion(e.target.value)} /></td>
                <td><input type="text" id="name" value={name} onChange={(e) => setName(e.target.value)} /></td>
              </tr>
            </tbody>
          </table>
          {id ? (
            <>
              <button onClick={handleUpdateUser} type="submit">Update</button>
              <button onClick={handleDeleteUser} type="submit">Delete</button>
            </>
          ) : (
            <button onClick={handleCreateUser} type="submit">Create</button>
          )}
        </form>
      )}
    </div>
  );
};

export default UserForm;
