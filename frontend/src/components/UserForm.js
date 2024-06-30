import React, { useState, useEffect, useParams } from 'react';
import { getUserById } from '../services/getUsers';
import { createUser } from '../services/createUser';

const UserForm = ({ initialData = {}, onSubmit }) => {
  const { id } = useParams();
  const [version, setVersion] = useState('');
  const [name, setName] = useState('');

  useEffect(() => {
    const fetchData = async () => {
      const response = await getUserById(id);
      const fetchedUser = response.data;
      setVersion(fetchedUser.version);
      setName(fetchedUser.name);
    };

    fetchData();
  }, [id]);

  const handleSubmit = async (event) => {
    event.preventDefault();
    const userData = { version, name }; 
    try {
      await createUser(userData);
      onSubmit(userData);
      setName('');
      setVersion('');
    } catch (error) {
      console.error('Error creating user:', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <label htmlFor="version">Version:</label>
      <input type="text" id="version" value={version} onChange={(e) => setVersion(e.target.value)} />
      <label htmlFor="name">Name:</label>
      <input type="text" id="name" value={name} onChange={(e) => setName(e.target.value)} />
      <button type="submit">{initialData.id ? 'Update' : 'Create'}</button>
    </form>
  );
};

export default UserForm;
