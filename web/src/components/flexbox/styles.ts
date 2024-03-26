import styled from 'styled-components';

interface FlexBoxContainerProps {
  width?: string;
  height?: string;
  justifyContent?: string;
}

interface FlexBoxItemProps {
  width?: string;
  height?: string;
  borderTop?: boolean;
  borderBottom?: boolean;
  borderLeft?: boolean;
  borderRight?: boolean;
  alignRight?: boolean;
  alignCenter?: boolean;
}

export const FlexBoxContainer = styled.div<FlexBoxContainerProps>`
  display: flex;
  width: ${props => props.width || '100%'};
  height: ${props => props.height || 'auto'};
  justify-content: ${props => props.justifyContent || 'flex-start'};
`;

export const FlexBoxItem = styled.div<FlexBoxItemProps>`
  width: ${props => props.width || 'auto'};
  height: ${props => props.height || 'auto'};
  ${props => props.alignRight && 'margin-left: auto;'}
  ${props => props.alignCenter && 'margin: 0 auto;'}
  border-top: ${props => props.borderTop ? '1px solid black' : 'none'};
  border-bottom: ${props => props.borderBottom ? '1px solid black' : 'none'};
  border-left: ${props => props.borderLeft ? '1px solid black' : 'none'};
  border-right: ${props => props.borderRight ? '1px solid black' : 'none'};
`;
