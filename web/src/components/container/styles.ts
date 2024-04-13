import styled from 'styled-components';

interface ContainerProps {
  margin?: string;
}

export const Container = styled.div<ContainerProps>`
  background-color: ${props => props.theme.colors.primary};
  margin: ${({ margin }) => margin || '10px 0 0 0'};
  padding: 10px;
`;