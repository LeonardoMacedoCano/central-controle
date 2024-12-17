import React, { useState, useRef, useEffect, useContext } from 'react';
import styled from 'styled-components';
import { FaBars, FaDollarSign, FaHome, FaServer, FaSignOutAlt } from 'react-icons/fa';
import { Link as RouterLink } from 'react-router-dom';
import { AuthContext } from '../contexts';

interface AppLayoutProps {
  children: React.ReactNode;
}

const AppLayout: React.FC<AppLayoutProps> = ({ children }) => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [activeSubmenu, setActiveSubmenu] = useState<string | null>(null);
  const sidebarRef = useRef<HTMLDivElement>(null);

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
    if (!isMenuOpen) {
      setActiveSubmenu(null);
    }
  };

  const handleLinkClick = () => {
    setIsMenuOpen(false);
    setActiveSubmenu(null);
  };

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (sidebarRef.current && !sidebarRef.current.contains(event.target as Node) && isMenuOpen) {
        setIsMenuOpen(false);
        setActiveSubmenu(null);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, [isMenuOpen]);

  return (
    <div style={{ display: 'flex', height: '100vh', overflow: 'hidden' }}>
      <div ref={sidebarRef}>
        <Sidebar 
          isOpen={isMenuOpen} 
          activeSubmenu={activeSubmenu}
          setActiveSubmenu={setActiveSubmenu}
          handleLinkClick={handleLinkClick}
        />
      </div>
      <MainContent isMenuOpen={isMenuOpen}>
        <Header toggleMenu={toggleMenu} />
        <PageContent>
          {children}
        </PageContent>
      </MainContent>
    </div>
  );
};

const Header: React.FC<{ toggleMenu: () => void }> = ({ toggleMenu }) => (
  <AppHeader>
    <MenuIcon onClick={toggleMenu}>
      <FaBars />
    </MenuIcon>
    <TitleHeader>Central de Controle</TitleHeader>
  </AppHeader>
);

interface SidebarProps {
  isOpen: boolean;
  activeSubmenu: string | null;
  setActiveSubmenu: React.Dispatch<React.SetStateAction<string | null>>;
  handleLinkClick: () => void;
}

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

const Sidebar: React.FC<SidebarProps> = ({ isOpen, activeSubmenu, setActiveSubmenu, handleLinkClick }) => {
  const auth = useContext(AuthContext);

  const handleLogout = () => {
    auth.signout();
    handleLinkClick();
  };

  const handleSubmenuToggle = (submenu: string) => {
    setActiveSubmenu(prev => (prev === submenu ? null : submenu));
  };

  const sidebarItems: SidebarItem[] = [
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
      to: "/servicos",
      Icon: FaServer,
      Text: "Serviços"
    },
    {
      to: "/",
      Icon: FaSignOutAlt,
      Text: "Sair",
      onClick: handleLogout }
  ];

  return (
    <AppSidebarContainer isActive={isOpen}>
      <AppSidebar>
        {sidebarItems.map((item, index) => (
          item.submenu ? (
            <SubmenuContainer key={index}>
              <MenuItem onClick={() => handleSubmenuToggle(item.Text)}>
                <item.Icon />
                {isOpen && <span>{item.Text}</span>}
              </MenuItem>
              {isOpen && activeSubmenu === item.Text && (
                <SubmenuContent>
                  {item.submenu.map((subItem, subIndex) => (
                    subItem.to ? (
                      <LinkSidebar key={`${index}-${subIndex}`} to={subItem.to} onClick={handleLinkClick}>
                        <SubMenuItem>{subItem.Text}</SubMenuItem>
                      </LinkSidebar>
                    ) : null
                  ))}
                </SubmenuContent>
              )}
            </SubmenuContainer>
          ) : item.to ? (
            <LinkSidebar key={index} to={item.to} onClick={item.onClick}>
              <MenuItem>
                <item.Icon />
                {isOpen && <span>{item.Text}</span>}
              </MenuItem>
            </LinkSidebar>
          ) : null
        ))}
      </AppSidebar>
    </AppSidebarContainer>
  );
};

export default AppLayout;

const MainContent = styled.div<{ isMenuOpen: boolean }>`
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  transition: margin-left 0.3s ease-in-out;
`;

const PageContent = styled.main`
  flex: 1;
  overflow-y: auto;
  padding: 20px;
`;

const AppSidebarContainer = styled.div<{ isActive: boolean }>`
  z-index: 1000;
  position: fixed;
  left: 0;
  top: 60px;
  height: 100%;
  width: ${({ isActive }) => isActive ? '250px' : '0'};
  color: ${({ theme }) => theme.colors.tertiary};
  background-color: ${({ theme }) => theme.colors.secondary};
  box-shadow: 0 2px 5px ${({ theme }) => theme.colors.tertiary};
  transition: width 0.3s ease-in-out;
  overflow-x: hidden;
`;

const AppHeader = styled.div`
  height: 60px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: ${({ theme }) => theme.colors.tertiary};
  background-color: ${({ theme }) => theme.colors.secondary};
  box-shadow: 0 2px 5px ${({ theme }) => theme.colors.tertiary};
  padding: 0 20px;
  flex-shrink: 0;

  > svg {
    width: 30px;
    height: 30px;
    cursor: pointer;
  }
`;

const MenuIcon = styled.div`
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  
  svg {
    font-size: 25px;
  }
`;

const AppSidebar = styled.div`
  height: 100vh;
  overflow-y: auto;
  border-top: 1px solid ${({ theme }) => theme.colors.tertiary};
`;

const LinkSidebar = styled(RouterLink)`
  text-decoration: none;
  color: inherit;
`;

const MenuItem = styled.div`
  display: flex;
  align-items: center;
  padding: 15px;
  height: 60px;
  cursor: pointer;
  transition: background-color 0.3s;
  border-bottom: 1px solid ${({ theme }) => theme.colors.tertiary};

  &:hover {
    background-color: ${({ theme }) => theme.colors.tertiary};
  }

  > svg {
    font-size: 20px;
    margin-right: 15px;
  }

  > span {
    white-space: nowrap;
  }
`;

const SubmenuContainer = styled.div`
  text-decoration: none;
  color: inherit;
`;

const SubmenuContent = styled.div`
  background-color: ${({ theme }) => theme.colors.secondary};
`;

const SubMenuItem = styled(MenuItem)`
  height: 50px;
  padding-left: 15px;
  background-color: ${({ theme }) => theme.colors.primary};
`;

const TitleHeader = styled.div`
  font-size: 18px;
  font-weight: bold;
  color: ${({ theme }) => theme.colors.white};
`;
