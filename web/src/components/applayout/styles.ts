import styled from 'styled-components';
import { Link as RouterLink } from 'react-router-dom';

export const AppHeader = styled.div`
  height: 50px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: ${({ theme }) => theme.colors.quaternary};
  background-color: ${({ theme }) => theme.colors.secondary};
  box-shadow: 0 0 5px 1px;
  padding: 0 20px;

  > svg {
    width: 30px;
    height: 30px;
    cursor: pointer;
  }
`;

export const TitleHeader = styled.h1`
  color: ${({ theme }) => theme.colors.quaternary};
  font-size: 24px;
  margin: 0;
`;

export const AppSidebarContainer = styled.div<{ isActive: boolean }>`
  display: flex;
  flex-direction: row;
  position: fixed;
  left: 0;
  top: 0;
  height: 100%;
  transform: ${({ isActive }) => isActive ? 'translateX(0)' : 'translateX(-300px)'};
  transition: transform 0.3s ease-in-out;
  z-index: 1000;
  color: ${({ theme }) => theme.colors.quaternary};
  background-color: ${({ isActive, theme }) => isActive ? theme.colors.secondary : 'transparent'};
  box-shadow: ${({ isActive }) => isActive ? '0 0 2px 1px' : 'none'};
`;

export const AppSidebar = styled.div`
  background-color: ${({ theme }) => theme.colors.primary};
  overflow-y: auto;
  width: 300px;
`;

export const AppSidebarItem = styled.div`
  display: flex;
  align-items: center;
  background-color: ${({ theme }) => theme.colors.secondary};
  border: 1px solid ${({ theme }) => theme.colors.tertiary};
  font-size: 20px;
  color: ${({ theme }) => theme.colors.white};
  padding: 10px 5px;
  cursor: pointer;
  border-radius: 10px;
  margin: 0 15px 20px;

  > svg {
    margin: 0 20px;
  }

  &:hover {
    color: ${({ theme }) => theme.colors.gray};
  }
`;

export const ContentSidebar = styled.div`
  margin-top: 50px;
`;

export const LinkSidebar = styled(RouterLink)`
  text-decoration: none;
  color: inherit;
`;

export const ToggleSidebarButton = styled.button`
  width: 30px;
  background-color: transparent;
  border: none;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
`;
