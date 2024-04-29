import React, {ReactNode } from 'react';
import * as C from './styles';

interface AppSidebarItemProps {
  Icon: React.FC;
  Text: ReactNode;
}

const AppSidebarItem: React.FC<AppSidebarItemProps> = ({ Icon, Text }) => {
  return (
    <C.AppSidebarItem>
      <Icon />
      {Text}
    </C.AppSidebarItem>
  );
};

export default AppSidebarItem;
