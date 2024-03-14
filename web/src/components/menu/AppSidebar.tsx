import { FC, } from 'react';
import * as C from './styles';
import { useContext } from 'react';
import { AuthContext } from '../../contexts/auth/AuthContext';
import {
  FaTimes,
  FaHome,
  FaCommentMedical,
  FaRegSun,
  FaCheckCircle,
  FaRegCalendarAlt,
  FaDollarSign,
  FaPlug
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

  return (
    <C.AppSidebar isActive={isActiveSidebar}>
      <FaTimes onClick={handleCloseSidebar} />
      <C.ContentSidebar onClick={handleCloseSidebar}>
        <C.LinkSidebar to="/">
          <AppSidebarItem Icon={FaHome} Text="Home" />
        </C.LinkSidebar>
        <C.LinkSidebar to="/controledespesas">
          <AppSidebarItem Icon={FaDollarSign} Text="Controle de despesas" />
        </C.LinkSidebar>
        <C.LinkSidebar to="/controletarefas">
          <AppSidebarItem Icon={FaCheckCircle} Text="Controle de tarefas" />
        </C.LinkSidebar>
        <C.LinkSidebar to="/controleideias">
          <AppSidebarItem Icon={FaCommentMedical} Text="Controle de ideias" />
        </C.LinkSidebar>
        <C.LinkSidebar to="/">
          <AppSidebarItem Icon={FaRegCalendarAlt} Text="Calendario" />
        </C.LinkSidebar>
        <C.LinkSidebar to="/">
          <AppSidebarItem Icon={FaRegSun} Text="Configuração" />
        </C.LinkSidebar>
        <C.LinkSidebar to="/" onClick={handleLogout}>
          <AppSidebarItem Icon={FaPlug} Text="Sair" />
        </C.LinkSidebar>
      </C.ContentSidebar>
    </C.AppSidebar>
  );
};

export default AppSidebar;