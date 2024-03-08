import React, {ReactNode } from 'react';
import * as C from './styles';

interface SidebarItemProps {
  Icon: React.FC;
  Text: ReactNode;
}

const SidebarItem: React.FC<SidebarItemProps> = ({ Icon, Text }) => {
  return (
    <C.SidebarItem>
      <Icon />
      {Text}
    </C.SidebarItem>
  );
};

export default SidebarItem;
