import styled, { css, CSSObject } from 'styled-components';

interface StyledButtonProps {
  variant?: 'table-add' | 'table-edit' | 'table-delete' | 'success' | 'info' | 'warning';
  width?: string;
  height?: string;
  style?: React.CSSProperties;
  disabled?: boolean;
}

const convertReactStyleToCSSObject = (style: React.CSSProperties): CSSObject => {
  return Object.fromEntries(
    Object.entries(style).map(([key, value]) => [key, value])
  );
};

const commonButtonStyles = css`
  height: 30px;
  width: 30px;
  border-radius: 50%;
  color: ${({ theme }) => theme.colors.white};
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 5px;
`;

export const StyledButton = styled.button<StyledButtonProps>`
  border: none;
  max-height: 100%;
  max-width: 100%;
  cursor: ${props => (props.disabled ? 'not-allowed' : 'pointer')};
  outline: none;
  transition: background-color 0.3s ease, opacity 0.3s ease;
  opacity: ${props => (props.disabled ? '0.3' : '1')};
  width: ${props => props.width || 'auto'};
  height: ${props => props.height || 'auto'};

  &:hover {
    opacity: ${props => (props.disabled ? '0.3' : '0.3')};
  }

  ${({ variant, theme }) => {
    switch (variant) {
      case 'table-add':
        return css`
          ${commonButtonStyles};
          background-color: ${theme.colors.success};
        `;
      case 'table-edit':
        return css`
          ${commonButtonStyles};
          background-color: ${theme.colors.info};
        `;
      case 'table-delete':
        return css`
          ${commonButtonStyles};
          background-color: ${theme.colors.warning};
        `;
      case 'success':
        return css`
          background-color: ${theme.colors.success};
          color: ${theme.colors.white};
        `;
      case 'info':
        return css`
          background-color: ${theme.colors.info};
          color: ${theme.colors.white};
        `;
      case 'warning':
        return css`
          background-color: ${theme.colors.warning};
          color: ${theme.colors.white};
        `;
      default:
        return css`
          background-color: ${theme.colors.primary};
          color: ${theme.colors.white};
        `;
    }
  }}

  ${props => props.style && css`${convertReactStyleToCSSObject(props.style)}`}
`;
