import { FC, } from 'react';
import { Container, Content, LinkContainer } from './styles';
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
  sidebar: boolean;
  setSidebar: (isActive: boolean) => void;
}

const Sidebar: FC<SidebarProps> = ({ sidebar, setSidebar }) => {
  const auth = useContext(AuthContext);

  const handleLogout = () => {
    auth.signout();
  }

  const closeSidebar = () => {
    setSidebar(false);
  };

  return (
    <Container sidebar={sidebar}>
      <FaTimes onClick={closeSidebar} />
      <Content onClick={closeSidebar}>
        <LinkContainer to="/">
          <SidebarItem Icon={FaHome} Text="Home" />
        </LinkContainer>
        <LinkContainer to="/controledespesas">
          <SidebarItem Icon={FaDollarSign} Text="Controle de despesas" />
        </LinkContainer>
        <LinkContainer to="/controletarefas">
          <SidebarItem Icon={FaCheckCircle} Text="Controle de tarefas" />
        </LinkContainer>
        <LinkContainer to="/controleideias">
          <SidebarItem Icon={FaCommentMedical} Text="Controle de ideias" />
        </LinkContainer>
        <LinkContainer to="/">
          <SidebarItem Icon={FaRegCalendarAlt} Text="Calendario" />
        </LinkContainer>
        <LinkContainer to="/">
          <SidebarItem Icon={FaRegSun} Text="Configuração" />
        </LinkContainer>
        <LinkContainer to="/" onClick={handleLogout}>
          <SidebarItem Icon={FaPlug} Text="Sair" />
        </LinkContainer>
      </Content>
    </Container>
  );
};

export default Sidebar;