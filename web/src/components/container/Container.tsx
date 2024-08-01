import { ReactNode, CSSProperties, FC } from 'react';
import * as C from './styles';

interface ContainerProps {
  children: ReactNode;
  margin?: string;
  padding?: string;
  style?: CSSProperties;
}

const Container: FC<ContainerProps> = ({ children, margin, padding, style }) => (
  <C.Container margin={margin} style={style} padding={padding}>
    {children}
  </C.Container>
);

export default Container;
