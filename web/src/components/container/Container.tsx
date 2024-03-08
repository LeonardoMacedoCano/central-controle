import React, { ReactNode } from 'react';
import * as C from './styles';

type ContainerProps = {
  children: ReactNode;
};

class Container extends React.Component<ContainerProps> {
  render() {
    const { children } = this.props;
    return (
      <C.Container>
        {children}
      </C.Container>
    );
  }
}

export default Container;