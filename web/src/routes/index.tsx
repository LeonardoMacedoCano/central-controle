import { Route, Routes } from 'react-router-dom';
import { FaHome, FaCog, FaDollarSign, FaSignOutAlt } from 'react-icons/fa';
import { Home } from '../pages/home/Home'; // Named export
import ConfiguracaoPage from '../pages/configuracao/ConfiguracaoPage';
import FluxoCaixaRoutes from './FluxoCaixaRoutes';
import { useContext } from 'react';
import { AuthContext } from '../contexts';

interface SubmenuItem {
  to: string;
  Text: string;
}

interface SidebarItem {
  to?: string;
  Icon: React.ComponentType;
  Text: string;
  onClick?: () => void;
  submenu?: SubmenuItem[];
}

export const sidebarItems: SidebarItem[] = [
  { 
    to: "/",
    Icon: FaHome,
    Text: "Home"
  },
  {
    Icon: FaDollarSign,
    Text: "Fluxo Caixa",
    submenu: [{ 
      to: "/lancamentos", 
      Text: "Lançamento" 
    }],
  },
  {
    to: "/configuracao", 
    Icon: FaCog, 
    Text: "Configuração" 
  },
  { 
    to: "/", 
    Icon: FaSignOutAlt, 
    Text: "Sair", 
    onClick: () => useContext(AuthContext).signout() 
  }
];

export const routes = [
  { path: "/", element: <Home /> },
  { path: "/configuracao", element: <ConfiguracaoPage /> },
];

const AppRoutes = () => (
  <Routes>
    {routes.map((route, index) => (
      <Route key={index} path={route.path} element={route.element} />
    ))}
    <FluxoCaixaRoutes />
  </Routes>
);

export default AppRoutes;