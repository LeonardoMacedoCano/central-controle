import React, { useState } from 'react';
import * as C from './styles';
import { FaBars } from 'react-icons/fa';
import Sidebar from '../sidebar';

const MainHeader: React.FC = () => {
  const [isActiveSidebar, setActiveSidebar] = useState(false);

  const showSidebar = () => setActiveSidebar(!isActiveSidebar);

  return (
    <C.MainHeader>
      <FaBars onClick={showSidebar} />
      <C.Title>Central de Controle</C.Title>
      {isActiveSidebar && <Sidebar isActive={isActiveSidebar} setActive={setActiveSidebar} />}
    </C.MainHeader>
  );
};

export default MainHeader;
