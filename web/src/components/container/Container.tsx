import { ReactNode, CSSProperties, FC } from 'react';
import styled from 'styled-components';

interface ContainerProps {
  children: ReactNode;
  height?: string;
  width?: string;
  margin?: string;
  padding?: string;
  backgroundColor?: string;
  style?: CSSProperties;
}

const Container: FC<ContainerProps> = ({ 
  children, 
  height,
  width,
  margin, 
  padding, 
  backgroundColor,
  style 
}) => (
  <StyledContainer 
    height={height}
    width={width}
    margin={margin} 
    padding={padding} 
    backgroundColor={backgroundColor} 
    style={style}
  >
    {children}
  </StyledContainer>
);

export default Container;

const StyledContainer = styled.div<ContainerProps>`
  height: ${({ height }) => height || 'auto'};
  width: ${({ width }) => width || 'auto'};
  margin: ${({ margin }) => margin || '0'};
  padding: ${({ padding }) => padding || '0'};
  background-color: ${({ backgroundColor, theme }) => backgroundColor || theme.colors.primary};
`;
