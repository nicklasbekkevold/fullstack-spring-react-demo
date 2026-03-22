import { Routes, Route } from 'react-router-dom';
import Role from '@/components/Role';
import RoleList from '@/components/RoleList';
import Unit from '@/components/Unit';
import UnitList from '@/components/UnitList';
import UserForm from '@/components/UserForm';
import UserList from '@/components/UserList';
import UserRoleForm from '@/components/UserRoleForm';
import UserRoleList from '@/components/UserRoleList';
import UserRoleSearch from '@/components/UserRoleSearch';
import Home from '@/pages/Home';

function App() {
  return (
      <Routes>
        <Route path="/" element={<Home />} />

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
        <Route path="/user-roles/search" element={<UserRoleSearch />} />
      </Routes>
  );
}

export default App;
