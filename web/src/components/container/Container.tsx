import React, { ReactNode, CSSProperties } from 'react';
import * as C from './styles';

type ContainerProps = {
  children: ReactNode;
  margin?: string;
  style?: CSSProperties;
};

class Container extends React.Component<ContainerProps> {
  render() {
    const { children, margin, style } = this.props;
    return (
      <C.Container margin={margin} style={style}>
        {children}
      </C.Container>
    );
  }
}

export default Container;
