import { ReactNode, CSSProperties, FC } from 'react';
import * as C from './styles';

interface ContainerProps {
  children: ReactNode;
  margin?: string;
  style?: CSSProperties;
}

const Container: FC<ContainerProps> = ({ children, margin, style }) => (
  <C.Container margin={margin} style={style}>
    {children}
  </C.Container>
);

export default Container;
