import styled from 'styled-components';
import { Link as RouterLink } from 'react-router-dom';

export const AppHeader = styled.div`
  height: 100px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: ${props => props.theme.colors.secondary};
  border-bottom: 1px solid ${props => props.theme.colors.gray};
  box-shadow: 0 0 20px 3px;
  padding: 0 20px;

  > svg {
    color: ${props => props.theme.colors.white};
    width: 30px;
    height: 30px;
    cursor: pointer;
  }
`;

export const TitleHeader = styled.h1`
  color: ${props => props.theme.colors.white};
  font-size: 24px;
  margin: 0;
`;

export const AppSidebarItem = styled.div`
  display: flex;
  align-items: center;
  background-color: ${props => props.theme.colors.primary};
  border: 1px solid ${props => props.theme.colors.gray};
  font-size: 20px;
  color: ${props => props.theme.colors.white};
  padding: 10px;
  cursor: pointer;
  border-radius: 10px;
  margin: 0 15px 20px;

  > svg {
    margin: 0 20px;
  }

  &:hover {
    color: ${props => props.theme.colors.gray};
  }
`;

interface AppSidebarProps {
  isActive: boolean;
}

export const AppSidebar = styled.div<AppSidebarProps>`
  background-color: ${props => props.theme.colors.secondary};
  border-right: 1px solid ${props => props.theme.colors.gray};
  position: fixed;
  height: 100%;
  top: 0px;
  left: 0px;
  width: 315px;
  left: ${(props) => (props.isActive ? '0' : '-100%')};
  animation: showSidebar 0.4s;
  z-index: 1000;

  > svg {
    position: fixed;
    color: ${props => props.theme.colors.white};
    width: 30px;
    height: 30px;
    margin-top: 32px;
    margin-left: 20px;
    cursor: pointer;
  }

  @keyframes showSidebar {
    from {
      opacity: 0;
      width: 0;
    }
    to {
      opacity: 1;
      width: 300px;
    }
  }
`;

export const ContentSidebar = styled.div`
  margin-top: 100px;
`;

export const LinkSidebar = styled(RouterLink)`
  text-decoration: none;
  color: inherit;
`;
