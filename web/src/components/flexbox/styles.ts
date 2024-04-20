import styled from 'styled-components';

interface FlexBoxContainerProps {
  width?: string;
  height?: string;
  flexDirection?: 'row' | 'column';
}

export const FlexBoxContainer = styled.div<FlexBoxContainerProps>`
  width: ${({ width }) => width || '100%'};
  height: ${({ height }) => height || '100%'};
  display: flex;
  flex-direction: ${({ flexDirection }) => flexDirection || 'row'};
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
  width: ${({ width }) => width || '100%'};
  height: ${({ height }) => height || '100%'};
  ${({ alignRight }) => alignRight && 'margin-left: auto;'}
  ${({ alignCenter }) => alignCenter && 'margin: 0 auto;'}
  border-top: ${({ borderTop, theme }) => borderTop ? `1px solid ${theme.colors.quaternary}` : 'none'};
  border-bottom: ${({ borderBottom, theme }) => borderBottom ? `1px solid ${theme.colors.quaternary}` : 'none'};
  border-left: ${({ borderLeft, theme }) => borderLeft ? `1px solid ${theme.colors.quaternary}` : 'none'};
  border-right: ${({ borderRight, theme }) => borderRight ? `1px solid ${theme.colors.quaternary}` : 'none'};
`;
