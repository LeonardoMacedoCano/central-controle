import styled from 'styled-components';
import { Link as RouterLink } from 'react-router-dom';

interface ContainerProps {
  sidebar: boolean;
}

export const Container = styled.div<ContainerProps>`
  background-color: ${props => props.theme.colors.secondary};
  border-right: 1px solid ${props => props.theme.colors.gray};
  position: fixed;
  height: 100%;
  top: 0px;
  left: 0px;
  width: 315px;
  left: ${(props) => (props.sidebar ? '0' : '-100%')};
  animation: showSidebar 0.4s;

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

export const Content = styled.div`
  margin-top: 100px;
`;

export const LinkContainer = styled(RouterLink)`
  text-decoration: none;
  color: inherit;
`;