import styled from 'styled-components';

interface FlexBoxContainerProps {
  width?: string;
  height?: string;
  flexDirection?: 'row' | 'column';
}

export const FlexBoxContainer = styled.div<FlexBoxContainerProps>`
  width: ${props => props.width || '100%'};
  height: ${props => props.height || '100%'};
  display: flex;
  flex-direction: ${props => props.flexDirection || 'row'};
`;

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

export const FlexBoxItem = styled.div<FlexBoxItemProps>`
  width: ${props => props.width || '100%'};
  height: ${props => props.height || '100%'};
  ${props => props.alignRight && 'margin-left: auto;'}
  ${props => props.alignCenter && 'margin: 0 auto;'}
  border-top: ${props => props.borderTop ? `1px solid ${props.theme.colors.tertiary}` : 'none'};
  border-bottom: ${props => props.borderBottom ? `1px solid ${props.theme.colors.tertiary}` : 'none'};
  border-left: ${props => props.borderLeft ? `1px solid ${props.theme.colors.tertiary}` : 'none'};
  border-right: ${props => props.borderRight ? `1px solid ${props.theme.colors.tertiary}` : 'none'};
`;
