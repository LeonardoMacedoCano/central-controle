import styled from 'styled-components';
import { Link as RouterLink } from 'react-router-dom';

export const MainContent = styled.div<{ isMenuOpen: boolean }>`
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  transition: margin-left 0.3s ease-in-out;
`;

export const PageContent = styled.main`
  flex: 1;
  overflow-y: auto;
  padding: 20px;
`;

export const AppSidebarContainer = styled.div<{ isActive: boolean }>`
  z-index: 1000;
  position: fixed;
  left: 0;
  top: 60px;
  height: 100%;
  width: ${({ isActive }) => isActive ? '250px' : '0'};
  color: ${({ theme }) => theme.colors.tertiary};
  background-color: ${({ theme }) => theme.colors.secondary};
  box-shadow: 0 2px 5px ${({ theme }) => theme.colors.tertiary};
  transition: width 0.3s ease-in-out;
  overflow-x: hidden;
`;

export const AppHeader = styled.div`
  height: 60px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: ${({ theme }) => theme.colors.tertiary};
  background-color: ${({ theme }) => theme.colors.secondary};
  box-shadow: 0 2px 5px ${({ theme }) => theme.colors.tertiary};
  padding: 0 20px;
  flex-shrink: 0;

  > svg {
    width: 30px;
    height: 30px;
    cursor: pointer;
  }
`;

export const MenuIcon = styled.div`
  height: 60px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  
  svg {
    font-size: 25px;
  }
`;

export const AppSidebar = styled.div`
  height: 100vh;
  overflow-y: auto;
  border-top: 1px solid ${({ theme }) => theme.colors.tertiary};
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
  border-bottom: 1px solid ${({ theme }) => theme.colors.tertiary};

  &:hover {
    background-color: ${({ theme }) => theme.colors.tertiary};
  }

  > svg {
    font-size: 20px;
    margin-right: 15px;
  }

  > span {
    white-space: nowrap;
  }
`;

export const SubmenuContainer = styled.div`
  text-decoration: none;
  color: inherit;
`;

export const SubmenuContent = styled.div`
  background-color: ${({ theme }) => theme.colors.secondary};
`;

export const SubMenuItem = styled(MenuItem)`
  height: 50px;
  padding-left: 15px;
  background-color: ${({ theme }) => theme.colors.primary};
`;

export const TitleHeader = styled.div`
  font-size: 18px;
  font-weight: bold;
  color: ${({ theme }) => theme.colors.white};
`;