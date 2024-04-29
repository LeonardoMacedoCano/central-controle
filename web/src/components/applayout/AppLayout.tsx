import React from 'react';
import AppHeader from './AppHeader';
import AppSidebar from './AppSideBar';

const AppLayout: React.FC = () => {
  return (
    <>
        <AppHeader/>
        <AppSidebar/>
    </>
  );
};

export default AppLayout;
