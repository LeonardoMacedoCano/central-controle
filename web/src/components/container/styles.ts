import styled from 'styled-components';

interface ContainerProps {
  margin?: string;
  padding?: string;
}

export const Container = styled.div<ContainerProps>`
  background-color: ${props => props.theme.colors.primary};
  margin: ${({ margin = '10px 0 0 0' }) => margin};
  padding: ${({ padding = '10px' }) => padding};
`;
