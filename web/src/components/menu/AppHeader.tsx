import React, { useState } from 'react';
import * as C from './styles';
import { FaBars } from 'react-icons/fa';
import AppSidebar from './AppSidebar';

const AppHeader: React.FC = () => {
  const [isActiveSidebar, setActiveSidebar] = useState(false);

  const toggleSidebar = () => {
    setActiveSidebar(prev => !prev);
  };

  return (
    <C.AppHeader>
      <FaBars onClick={toggleSidebar} />
      <C.TitleHeader>
        Central de Controle
      </C.TitleHeader>
      {isActiveSidebar && <AppSidebar isActiveSidebar={isActiveSidebar} setActiveSidebar={setActiveSidebar} />}
    </C.AppHeader>
  );
};

export default AppHeader;
