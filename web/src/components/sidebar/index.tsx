import { FC, } from 'react';
import * as C from './styles';
import { useContext } from 'react';
import { AuthContext } from '../../contexts/auth/AuthContext';
import SidebarItem from '../sidebaritem';
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

interface SidebarProps {
  isActive: boolean;
  setActive: (isActive: boolean) => void;
}

const Sidebar: FC<SidebarProps> = ({ isActive, setActive }) => {
  const auth = useContext(AuthContext);

  const handleLogout = () => {
    auth.signout();
  }

  const closeSidebar = () => {
    setActive(false);
  };

  return (
    <C.Sidebar isActive={isActive}>
      <FaTimes onClick={closeSidebar} />
      <C.Content onClick={closeSidebar}>
        <C.Link to="/">
          <SidebarItem Icon={FaHome} Text="Home" />
        </C.Link>
        <C.Link to="/controledespesas">
          <SidebarItem Icon={FaDollarSign} Text="Controle de despesas" />
        </C.Link>
        <C.Link to="/controletarefas">
          <SidebarItem Icon={FaCheckCircle} Text="Controle de tarefas" />
        </C.Link>
        <C.Link to="/controleideias">
          <SidebarItem Icon={FaCommentMedical} Text="Controle de ideias" />
        </C.Link>
        <C.Link to="/">
          <SidebarItem Icon={FaRegCalendarAlt} Text="Calendario" />
        </C.Link>
        <C.Link to="/">
          <SidebarItem Icon={FaRegSun} Text="Configuração" />
        </C.Link>
        <C.Link to="/" onClick={handleLogout}>
          <SidebarItem Icon={FaPlug} Text="Sair" />
        </C.Link>
      </C.Content>
    </C.Sidebar>
  );
};

export default Sidebar;