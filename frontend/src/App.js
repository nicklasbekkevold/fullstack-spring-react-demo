import { Routes, Route } from 'react-router-dom';
import UserList from './components/UserList';
import Unit from './components/Unit';
import UnitList from './components/UnitList';
import UserForm from './components/UserForm';

function App() {
  return (
    <main>
      <nav>
        <ul>
          <li>
            <a href="/">Home</a>
          </li>
          <li>
            <a href="/units">Units</a>
          </li>
          <li>
            <a href="/users">User</a>
          </li>
        </ul>
      </nav>
      <Routes>
        <Route path="/" element={<div>Welcome to the application!</div>} /> 
        <Route path="/units" element={<UnitList />} /> 
        <Route path="/units/:id" element={<Unit />} /> 
        <Route path="/users" element={<UserList />} /> 
        <Route path="/users/new" element={<UserForm />} />
        <Route path="/users/:id" element={<UserForm editMode={true} />} />
      </Routes>
    </main>
  );
}

export default App;
