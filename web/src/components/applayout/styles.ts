import styled from 'styled-components';
import { Link as RouterLink } from 'react-router-dom';

export const MainContent = styled.div<{ isMenuOpen: boolean }>`
  flex: 1;
  margin-left: ${({ isMenuOpen }) => isMenuOpen ? '250px' : '60px'};
  transition: margin-left 0.3s ease-in-out;
`;

export const PageContent = styled.main``;

export const AppSidebarContainer = styled.div<{ isActive: boolean }>`
  z-index: 1000;
  overflow: hidden;
  position: fixed;
  left: 0;
  top: 0;
  height: 100%;
  width: ${({ isActive }) => isActive ? '250px' : '60px'};  
  color: ${({ theme }) => theme.colors.tertiary};
  background-color: ${({ theme }) => theme.colors.secondary};
  box-shadow: 0 0 5px 2px;
  transition: width 0.3s ease-in-out;
`;

export const MenuIcon = styled.div<{ isActive: boolean }>`
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: left 0.3s ease-in-out;
  
  svg {
    font-size: 25px;
    position: absolute;
    left: ${({ isActive }) => isActive ? '210px' : '10px'};
  }
`;

export const AppSidebar = styled.div`
  height: 100%;
  overflow-y: auto;
`;

export const LinkSidebar = styled(RouterLink)`
  text-decoration: none;
  color: inherit;
`;

export const MenuItem = styled.div`
  display: flex;
  align-items: center;
  padding: 15px;
  height: 60px;
  cursor: pointer;
  transition: background-color 0.3s;
  border-bottom: 2px solid ${({ theme }) => theme.colors.tertiary};

  &:hover {
    background-color: ${({ theme }) => theme.colors.tertiary};
  }

  > svg {
    margin-right: 15px;
  }

  > span {
    white-space: nowrap;
  }
`;

export const SubmenuContainer = styled.div`
`;

export const SubmenuContent = styled.div`
  background-color: ${({ theme }) => theme.colors.secondary};
`;

export const SubMenuItem = styled(MenuItem)`
  padding-left: 50px;
  height: 60px;
  background-color: ${({ theme }) => theme.colors.primary};
`;

export const AppHeader = styled.div`
  height: 60px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: ${({ theme }) => theme.colors.tertiary};
  background-color: ${({ theme }) => theme.colors.secondary};
  box-shadow: 0 0 5px 2px;
  padding: 0 20px;

  > svg {
    width: 30px;
    height: 30px;
    cursor: pointer;
  }
`;

export const TitleHeader = styled.div`
  font-size: 18px;
  font-weight: bold;
  color: ${({ theme }) => theme.colors.white};
`;