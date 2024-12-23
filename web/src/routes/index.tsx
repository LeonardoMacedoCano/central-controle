import { Routes, Route } from 'react-router-dom';
import { Home } from '../pages/home/Home';
import FluxoCaixaRoutes from './FluxoCaixaRoutes';
import ServicoListPage from '../pages/servicos/ServicoListPage';

const defaultRoutes = [
  { path: "/", element: <Home /> },
  { path: "/servicos", element: <ServicoListPage /> },
];

const combinedRoutes = [
  ...defaultRoutes,
  ...FluxoCaixaRoutes,
];

const AppRoutes: React.FC = () => (
  <Routes>
    {combinedRoutes.map((route, index) => (
      <Route key={index} path={route.path} element={route.element} />
    ))}
  </Routes>
);

export default AppRoutes;
