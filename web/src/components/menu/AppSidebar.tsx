import { FC, useContext } from 'react';
import * as C from './styles';
import { AuthContext } from '../../contexts/auth/AuthContext';
import {
  FaTimes,
  FaHome,
  FaCommentMedical,
  FaRegSun,
  FaCheckCircle,
  FaRegCalendarAlt,
  FaDollarSign,
  FaSignOutAlt
} from 'react-icons/fa';
import AppSidebarItem from './AppSidebarItem';

interface AppSidebarProps {
  isActiveSidebar: boolean;
  setActiveSidebar: (isActiveSidebar: boolean) => void;
}

const AppSidebar: FC<AppSidebarProps> = ({ isActiveSidebar, setActiveSidebar }) => {
  const auth = useContext(AuthContext);

  const handleLogout = () => {
    auth.signout();
  };

  const handleCloseSidebar = () => {
    setActiveSidebar(false);
  };

  const sidebarItems = [
    { to: "/", Icon: FaHome, Text: "Home" },
    { to: "/controledespesas", Icon: FaDollarSign, Text: "Controle de despesas" },
    { to: "/controletarefas", Icon: FaCheckCircle, Text: "Controle de tarefas" },
    { to: "/controleideias", Icon: FaCommentMedical, Text: "Controle de ideias" },
    { to: "/", Icon: FaRegCalendarAlt, Text: "Calendario" },
    { to: "/", Icon: FaRegSun, Text: "Configuração" },
    { to: "/", Icon: FaSignOutAlt, Text: "Sair", onClick: handleLogout },
  ];

  return (
    <C.AppSidebar isActive={isActiveSidebar}>
      <FaTimes onClick={handleCloseSidebar} />
      <C.ContentSidebar onClick={handleCloseSidebar}>
        {sidebarItems.map((item, index) => (
          <C.LinkSidebar key={index} to={item.to} onClick={item.onClick}>
            <AppSidebarItem Icon={item.Icon} Text={item.Text} />
          </C.LinkSidebar>
        ))}
      </C.ContentSidebar>
    </C.AppSidebar>
  );
};

export default AppSidebar;
