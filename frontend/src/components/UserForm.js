import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { createUser, getUserById, updateUser, deleteUser } from '../services/user';

const UserForm = () => {
  const { id } = useParams();
  const [apiVersion, setApiVersion] = useState(1);
  const [version, setVersion] = useState(1);
  const [name, setName] = useState('');

  useEffect(() => {
    if (id) {
      const fetchData = async () => {
        const fetchedUser = await getUserById(id);
        setVersion(fetchedUser.version);
        setApiVersion(fetchedUser.version);
        setName(fetchedUser.name);
      };
      
      fetchData();
    }
  }, [id]);

  const handleCreateUser = async (event) => {
    event.preventDefault();
    const userData = { name }; 
    try {
      await createUser(userData);
      setName('');
      setVersion('');
      setApiVersion('');
    } catch (error) {
      console.error('Error creating user:', error);
    }
  };

  const handleUpdateUser = async (event) => {
    event.preventDefault();
    const userData = { version, name }; 
    try {
      await updateUser(id, apiVersion, userData);
      setName('');
      setVersion('');
      setApiVersion('');
    } catch (error) {
      console.error('Error updating user:', error);
    }
  };

  const handleDeleteUser = async (event) => {
    event.preventDefault();
    try {
      await deleteUser(id, apiVersion);
      setName('');
      setVersion('');
      setApiVersion('');
    } catch (error) {
      console.error('Error deleting user:', error);
    }
  };

  return (
    <form>
      {id && (
        <>
          <label htmlFor="version">Current version:</label>
          <input type="text" id="apiVersion" value={apiVersion} onChange={(e) => setApiVersion(e.target.value)} />
          <br/>
          <br/>
        </>
      )}
      <label htmlFor="version">Version:</label>
      <input type="text" id="version" value={version} onChange={(e) => setVersion(e.target.value)} />
      <label htmlFor="name">Name:</label>
      <input type="text" id="name" value={name} onChange={(e) => setName(e.target.value)} />
      {id ? (
        <>
          <button onClick={handleUpdateUser} type="submit">Update</button>
          <button onClick={handleDeleteUser} type="submit">Delete</button>
        </>
      ) : (
        <button onClick={handleCreateUser} type="submit">Create</button>
      )}
    </form>
  );
};

export default UserForm;
