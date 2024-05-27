import styled, { css } from 'styled-components';
import { getVariantColor } from '../../utils/styledUtils';

interface FieldValueProps {
  width?: string;
  maxWidth?: string;
  maxHeight?: string;
  inline?: boolean;
  padding?: string;
}

export const FieldValue = styled.div<FieldValueProps>`
  width: ${({ width }) => width || '100%'};
  max-width: ${({ maxWidth }) => maxWidth || 'none'};
  max-height: ${({ maxHeight }) => maxHeight || 'none'};
  height: 100%;
  padding: ${({ padding }) => padding || '5px'};
  display: flex;
  flex-direction: ${({ inline }) => (inline ? 'row' : 'column')};
  align-items: ${({ inline }) => (inline ? 'center' : 'stretch')};
`;

export const Label = styled.span`
  color: ${({ theme }) => theme.colors.quaternary};
  font-weight: bold;
  font-size: 15px;
  height: 100%;
  display: flex;
  align-items: center;
`;

interface StyledInputProps {
  inputWidth?: string;
  inline?: boolean;
  readOnly?: boolean;
  variant?: 'success' | 'info' | 'warning';
}

export const StyledInput = styled.input<StyledInputProps>`
  width: ${({ inputWidth }) => inputWidth || '100%'};
  font-size: 15px;
  height: 100%;
  outline: none;
  background-color: transparent;
  margin-left: ${({ inline }) => (inline ? '5px' : 'none')};
  cursor: ${({ readOnly }) => (readOnly ? 'not-allowed' : 'pointer')};

  &::-webkit-calendar-picker-indicator {
    filter: invert(100%);
  }

  ${({ variant, theme }) =>
    variant &&css`
      color: ${getVariantColor(variant, theme)};
    `
  }
`;

export const StyledSelect = styled.select<StyledInputProps>`
  width: ${({ inputWidth }) => inputWidth || '100%'};
  font-size: 15px;
  height: 100%;
  outline: none;
  background-color: transparent;
  margin-left: ${({ inline }) => (inline ? '5px' : 'none')};

  ${({ variant, theme }) =>
    variant &&
    css`
      color: ${getVariantColor(variant, theme)};
    `}

  option {
    color: ${({ theme }) => theme.colors.white};
    background-color: ${({ theme }) => theme.colors.primary};
  }
`;

export const Icon = styled.div`
  height: 100%;
  width: auto;
`;
