import React from 'react';
import * as C from './styles';
import { FaBars } from 'react-icons/fa';

interface AppHeaderProps {
  toggleMenu: () => void;
}

const AppHeader: React.FC<AppHeaderProps> = ({ toggleMenu }) => {
  return (
    <C.AppHeader>
      <C.MenuIcon onClick={toggleMenu}>
        <FaBars />
      </C.MenuIcon>
      <C.TitleHeader>
        Central de Controle
      </C.TitleHeader>
    </C.AppHeader>
  );
};

export default AppHeader;