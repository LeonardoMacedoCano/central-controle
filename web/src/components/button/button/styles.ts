import styled, { css } from 'styled-components';
import { convertReactStyleToCSSObject } from '../../../utils/styledUtils';

interface StyledButtonProps {
  variant?: 'table-add' | 'table-edit' | 'table-delete' | 'success' | 'info' | 'warning';
  width?: string;
  height?: string;
  disabled?: boolean;
  style?: React.CSSProperties;
}

const commonButtonTableStyles = css`
  height: 30px;
  width: 30px;
  border-radius: 50%;
  color: ${({ theme }) => theme.colors.white};
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 5px;
`;

const getButtonVariantStyles = (variant: StyledButtonProps['variant'], theme: any) => {
  switch (variant) {
    case 'table-add':
      return css`
        ${commonButtonTableStyles};
        background-color: ${theme.colors.success};
      `;
    case 'table-edit':
      return css`
        ${commonButtonTableStyles};
        background-color: ${theme.colors.info};
      `;
    case 'table-delete':
      return css`
        ${commonButtonTableStyles};
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
};

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

  ${({ variant, theme }) => getButtonVariantStyles(variant, theme)}

  ${props => props.style && css`${convertReactStyleToCSSObject(props.style)}`}
`;
