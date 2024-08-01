import React, { useState, useRef, useEffect } from 'react';
import { Outlet } from 'react-router-dom';
import AppHeader from './AppHeader';
import AppSidebar from './AppSideBar';
import * as C from './styles';
import Container from '../container/Container';

const AppLayout: React.FC = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(true);
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
    <Container margin='none' padding='0' style={{display: 'flex'}}>
      <div ref={sidebarRef}>
        <AppSidebar 
          isOpen={isMenuOpen} 
          toggleMenu={toggleMenu} 
          activeSubmenu={activeSubmenu}
          setActiveSubmenu={setActiveSubmenu}
          handleLinkClick={handleLinkClick}
        />
      </div>
      <C.MainContent isMenuOpen={isMenuOpen}>
        <AppHeader />
        <C.PageContent>
          <Outlet />
        </C.PageContent>
      </C.MainContent>
    </Container>
  );
};

export default AppLayout;