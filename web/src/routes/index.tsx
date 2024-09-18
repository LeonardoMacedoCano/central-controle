import { Routes, Route } from 'react-router-dom';
import { Home } from '../pages/home/Home';
import ConfiguracaoPage from '../pages/configuracao/ConfiguracaoPage';
import FluxoCaixaRoutes from './FluxoCaixaRoutes';

const defaultRoutes = [
  { path: "/", element: <Home /> },
  { path: "/configuracao", element: <ConfiguracaoPage /> },
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
