import { CSSProperties, FC, ReactNode } from 'react';
import * as C from './styles';

interface FlexBoxProps {
  children: ReactNode;
  width?: string;
  height?: string;
  flexDirection?: 'row' | 'column';
  borderTop?: boolean;
  borderBottom?: boolean;
  borderRight?: boolean;
  borderLeft?: boolean;
  style?: CSSProperties;
}

interface FlexBoxChildProps {
  children: ReactNode;
  width?: string;
  height?: string;
  borderTop?: boolean;
  borderBottom?: boolean;
  borderRight?: boolean;
  borderLeft?: boolean;
  alignCenter?: boolean;
  alignRight?: boolean;
}

export const FlexBox: FC<FlexBoxProps> & { Item: FC<FlexBoxChildProps> } = ({ children, ...rest }) => (
  <C.FlexBoxContainer {...rest}>
    {children}
  </C.FlexBoxContainer>
);

FlexBox.Item = ({ children, ...rest }) => (
  <C.FlexBoxItem {...rest}>
    {children}
  </C.FlexBoxItem>
);

export default FlexBox;
