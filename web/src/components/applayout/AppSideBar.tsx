import React, { useContext } from 'react';
import * as C from './styles';
import { AuthContext } from '../../contexts/auth/AuthContext';
import {
  FaHome,
  FaCog,
  FaDollarSign,
  FaSignOutAlt
} from 'react-icons/fa';

interface AppSidebarProps {
  isOpen: boolean;
  activeSubmenu: string | null;
  setActiveSubmenu: React.Dispatch<React.SetStateAction<string | null>>;
  handleLinkClick: () => void;
}

const AppSidebar: React.FC<AppSidebarProps> = ({ 
  isOpen, 
  activeSubmenu, 
  setActiveSubmenu, 
  handleLinkClick 
}) => {
  const auth = useContext(AuthContext);

  const handleLogout = () => {
    auth.signout();
    handleLinkClick();
  };

  const handleSubmenuToggle = (submenu: string) => {
    setActiveSubmenu(prev => prev === submenu ? null : submenu);
  };

  const sidebarItems = [
    { to: "/", Icon: FaHome, Text: "Home" },
    {
      Icon: FaDollarSign,
      Text: "Fluxo Caixa",
      submenu: [
        { to: "/lancamentos", Text: "Lançamento" }
      ]
    },
    { to: "/configuracao", Icon: FaCog, Text: "Configuração" },
    { to: "/", Icon: FaSignOutAlt, Text: "Sair", onClick: handleLogout },
  ];

  return (
    <C.AppSidebarContainer isActive={isOpen}>
      <C.AppSidebar>
        {sidebarItems.map((item, index) => (
          item.submenu ? (
            <C.SubmenuContainer key={index}>
              <C.MenuItem onClick={() => handleSubmenuToggle(item.Text)}>
                <item.Icon />
                {isOpen && (<span>{item.Text}</span>)}
              </C.MenuItem>
              {isOpen && activeSubmenu === item.Text && (
                <C.SubmenuContent>
                  {item.submenu.map((subItem, subIndex) => (
                    <C.LinkSidebar key={`${index}-${subIndex}`} to={subItem.to} onClick={handleLinkClick}>
                      <C.SubMenuItem>
                        {subItem.Text}
                      </C.SubMenuItem>
                    </C.LinkSidebar>
                  ))}
                </C.SubmenuContent>
              )}
            </C.SubmenuContainer>
          ) : (
            <C.LinkSidebar key={index} to={item.to} onClick={item.onClick || handleLinkClick}>
              <C.MenuItem>
                <item.Icon />
                {isOpen && <span>{item.Text}</span>}
              </C.MenuItem>
            </C.LinkSidebar>
          )
        ))}
      </C.AppSidebar>
    </C.AppSidebarContainer>
  );
};

export default AppSidebar;