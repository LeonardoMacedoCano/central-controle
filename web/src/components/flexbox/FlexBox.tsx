import React from 'react';
import * as C from './styles';

interface FlexBoxProps {
  children: React.ReactNode;
  width?: string;
  height?: string;
}

interface FlexBoxChildProps {
  children: React.ReactNode;
  width?: string;
  height?: string;
  borderRight?: boolean;
  borderLeft?: boolean;
  flex?: number;
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
