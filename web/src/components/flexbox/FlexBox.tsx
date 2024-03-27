import React from 'react';
import * as C from './styles';

interface FlexBoxProps {
  children: React.ReactNode;
  width?: string;
  height?: string;
  justifyContent?: string;
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
    const { children, width, height, justifyContent } = this.props;
    return (
      <C.FlexBoxContainer width={width} height={height} justifyContent={justifyContent}>
        {children}
      </C.FlexBoxContainer>
    );
  }
}

export default FlexBox;
