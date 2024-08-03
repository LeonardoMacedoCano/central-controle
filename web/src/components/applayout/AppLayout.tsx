import React, { useState, useRef, useEffect, ReactNode } from 'react';
import AppHeader from './AppHeader';
import AppSidebar from './AppSideBar';
import * as C from './styles';
import Container from '../container/Container';

interface AppLayoutProps {
  children: ReactNode;
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
    <Container 
      margin='none'
      padding='0'
      style={{display: 'flex', height: '100vh', overflow: 'hidden'}}>
      <div ref={sidebarRef}>
        <AppSidebar 
          isOpen={isMenuOpen} 
          activeSubmenu={activeSubmenu}
          setActiveSubmenu={setActiveSubmenu}
          handleLinkClick={handleLinkClick}
        />
      </div>
      <C.MainContent isMenuOpen={isMenuOpen}>
        <AppHeader toggleMenu={toggleMenu} />
        <C.PageContent>
          {children}
        </C.PageContent>
      </C.MainContent>
    </Container>
  );
};

export default AppLayout;