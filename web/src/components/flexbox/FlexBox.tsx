import React from 'react';
import * as C from './styles';

interface FlexBoxProps {
  children: React.ReactNode;
  width?: string;
  height?: string;
  flexDirection?: 'row' | 'column';
}

interface FlexBoxChildProps {
  children: React.ReactNode;
  width?: string;
  height?: string;
  borderTop?: boolean;
  borderBottom?: boolean;
  borderRight?: boolean;
  borderLeft?: boolean;
  alignCenter?: boolean;
  alignRight?: boolean;
}

class FlexBox extends React.Component<FlexBoxProps> {
  static Item: React.FC<FlexBoxChildProps> = (props) => {
    const { children, ...rest } = props;
    return (
      <C.FlexBoxItem {...rest}>
        {children}
      </C.FlexBoxItem>
    );
  };

  render() {
    const { children, ...rest } = this.props;
    return (
      <C.FlexBoxContainer {...rest}>
        {children}
      </C.FlexBoxContainer>
    );
  }
}

export default FlexBox;
