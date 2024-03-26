import React, { ReactNode } from 'react';
import * as C from './styles';

interface FlexBoxProps {
  children: ReactNode;
  width?: string;
  height?: string;
  justifyContent?: string;
}

class FlexBox extends React.Component<FlexBoxProps> {
  render() {
    const { children, width, height, justifyContent } = this.props;
    return (
      <C.FlexBoxContainer width={width} height={height} style={{ justifyContent }}>
        {React.Children.map(children, child => (
          child ? (
            <C.FlexBoxItem 
              width={(child as any).props.width} 
              height={(child as any).props.height}
              alignRight={(child as any).props.alignRight}
            >
              {child}
            </C.FlexBoxItem>
          ) : null
        ))}
      </C.FlexBoxContainer>
    );
  }
}

export default FlexBox;

export const FlexBoxElement = ({ children, alignRight, ...props }: any) => {
  return (
    <C.FlexBoxItem {...props} alignRight={alignRight}>
      {children}
    </C.FlexBoxItem>
  );
};
