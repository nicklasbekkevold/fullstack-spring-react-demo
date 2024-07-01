import { Routes, Route } from 'react-router-dom';
import Role from './components/Role';
import RoleList from './components/RoleList';
import Unit from './components/Unit';
import UnitList from './components/UnitList';
import UserForm from './components/UserForm';
import UserList from './components/UserList';
import UserRoleForm from './components/UserRoleForm';
import UserRoleList from './components/UserRoleList';

function App() {
  return (
    <main>
      <nav>
        <ul>
          <li>
            <a href="/">Home</a>
          </li>
          <li>
            <a href="/roles">Roles</a>
          </li>
          <li>
            <a href="/units">Units</a>
          </li>
          <li>
            <a href="/users">User</a>
          </li>
          <li>
            <a href="/user-roles">User Roles</a>
          </li>
        </ul>
      </nav>
      <Routes>
        <Route path="/" element={<div>Welcome to the application!</div>} /> 
        
        <Route path="/roles" element={<RoleList />} /> 
        <Route path="/roles/:id" element={<Role />} /> 
        
        <Route path="/units" element={<UnitList />} /> 
        <Route path="/units/:id" element={<Unit />} /> 
        
        <Route path="/users" element={<UserList />} /> 
        <Route path="/users/new" element={<UserForm />} />
        <Route path="/users/:id" element={<UserForm />} />

        <Route path="/user-roles" element={<UserRoleList />} /> 
        <Route path="/user-roles/new" element={<UserRoleForm />} />
        <Route path="/user-roles/:id" element={<UserRoleForm />} /> 
      </Routes>
    </main>
  );
}

export default App;
