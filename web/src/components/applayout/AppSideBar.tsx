import { useContext, useState } from 'react';
import * as C from './styles';
import { AuthContext } from '../../contexts/auth/AuthContext';
import {
  FaHome,
  FaCommentMedical,
  FaRegSun,
  FaCheckCircle,
  FaRegCalendarAlt,
  FaDollarSign,
  FaSignOutAlt,
  FaAngleLeft,
  FaAngleRight
} from 'react-icons/fa';
import AppSidebarItem from './AppSideBarItem';

const AppSidebar: React.FC = () => {
  const [isActiveSidebar, setActiveSidebar] = useState(false);
  const auth = useContext(AuthContext);

  const handleLogout = () => {
    auth.signout();
  };

  const handleCloseSidebar = () => {
    setActiveSidebar(false);
  };

  const handleToggleSidebar = () => {
    setActiveSidebar(prevState => !prevState); // Inverte o estado atual
  };

  const sidebarItems = [
    { to: "/", Icon: FaHome, Text: "Home" },
    { to: "/controledespesas", Icon: FaDollarSign, Text: "Controle de despesas" },
    { to: "/controletarefas", Icon: FaCheckCircle, Text: "Controle de tarefas" },
    { to: "/controleideias", Icon: FaCommentMedical, Text: "Controle de ideias" },
    { to: "/", Icon: FaRegCalendarAlt, Text: "Calendario" },
    { to: "/configuracao", Icon: FaRegSun, Text: "Configuração" },
    { to: "/", Icon: FaSignOutAlt, Text: "Sair", onClick: handleLogout },
  ];

  return (
    <C.AppSidebarContainer isActive={isActiveSidebar}>

      <C.AppSidebar>
        <C.ContentSidebar onClick={handleCloseSidebar}>
          {sidebarItems.map((item, index) => (
            <C.LinkSidebar key={index} to={item.to} onClick={item.onClick}>
              <AppSidebarItem Icon={item.Icon} Text={item.Text} />
            </C.LinkSidebar>
          ))}
        </C.ContentSidebar>
      </C.AppSidebar>

      <C.ToggleSidebarButton onClick={handleToggleSidebar}>
        {isActiveSidebar ? <FaAngleLeft /> : <FaAngleRight />}
      </C.ToggleSidebarButton>
    </C.AppSidebarContainer>
  );
};

export default AppSidebar;

