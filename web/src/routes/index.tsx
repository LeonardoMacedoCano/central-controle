import { Routes, Route } from 'react-router-dom';
import { Home } from '../pages/home/Home';
import FluxoCaixaRoutes from './FluxoCaixaRoutes';
import ServicoListPage from '../pages/servicos/ServicoListPage';
import NotificacaoListPage from '../pages/notificacao/NotificacaoListPage';
import NotificacaoViewPage from '../pages/notificacao/NotificacaoViewPage';
import UsuarioFormPage from '../pages/usuario/UsuarioFormPage';
import JobErrorLogViewPage from '../pages/jobErrorLog/JobErrorLogViewPage';

const defaultRoutes = [
  { path: "/", element: <Home /> },
  { path: "/servicos", element: <ServicoListPage /> },
  { path: "/notificacoes", element: <NotificacaoListPage /> },
  { path: "/notificacao/:id", element: <NotificacaoViewPage /> },
  { path: "/usuario", element: <UsuarioFormPage /> },
  { path: "/job-error/:id", element: <JobErrorLogViewPage /> },
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
