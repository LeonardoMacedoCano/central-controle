import React, { useState } from 'react';
import { Container, Title  } from './styles';
import { FaBars } from 'react-icons/fa';
import Sidebar from '../sidebar';

const MainHeader: React.FC = () => {
  const [sidebar, setSidebar] = useState(false);

  const showSidebar = () => setSidebar(!sidebar);

  return (
    <Container>
      <FaBars onClick={showSidebar} />
      <Title>Central de Controle</Title>
      {sidebar && <Sidebar sidebar={sidebar} setSidebar={setSidebar} />}
    </Container>
  );
};

export default MainHeader;
