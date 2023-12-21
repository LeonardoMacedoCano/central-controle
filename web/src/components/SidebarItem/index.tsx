import React, {ReactNode } from 'react';
import { Container } from './styles';

interface SidebarItemProps {
  Icon: React.FC;
  Text: ReactNode;
}

const SidebarItem: React.FC<SidebarItemProps> = ({ Icon, Text }) => {
  return (
    <Container>
      <Icon />
      {Text}
    </Container>
  );
};

export default SidebarItem;
