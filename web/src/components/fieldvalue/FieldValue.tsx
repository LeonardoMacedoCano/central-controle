import React from 'react';
import styled, { css } from 'styled-components';
import { getVariantColor } from '../../utils/styledUtils';
import { formatarDataParaAnoMes, formatarDataParaStringYMD, formatarStringParaData } from '../../utils/DateUtils';
import { formatarNumeroComZerosAEsquerda } from '../../utils/ValorUtils';
import { SelectValue } from '../../types/SelectValue';

type FieldValueProps = {
  type: 'string' | 'number' | 'boolean' | 'date' | 'month' | 'select';
  value: string | number | boolean | SelectValue;
  variant?: 'success' | 'info' | 'warning';
  description?: string;
  hint?: string;
  editable?: boolean;
  width?: string;
  maxWidth?: string;
  maxHeight?: string;
  minValue?: number;
  maxValue?: number;
  inputWidth?: string;
  inline?: boolean;
  options?: SelectValue[];
  icon?: React.ReactNode;
  padding?: string;
  placeholder?: string;
  onUpdate?: (value: any) => void;
  onKeyDown?: React.KeyboardEventHandler<HTMLInputElement>;
};

const FieldValue: React.FC<FieldValueProps> = ({
  type,
  value,
  variant,
  description,
  hint,
  editable,
  width,
  maxWidth,
  maxHeight,
  minValue,
  maxValue,
  inputWidth,
  inline,
  options,
  icon,
  padding,
  placeholder,
  onUpdate,
  onKeyDown,
}) => {
  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLSelectElement>) => {
    if (onUpdate) {
      let formattedValue: any = event.target.value;

      if (type === 'number') {
        formattedValue = parseFloat(event.target.value);
        if ((minValue || minValue == 0) && formattedValue < minValue) {
          formattedValue = minValue;
        } else if (maxValue && formattedValue > maxValue) {
          formattedValue = maxValue;
        }
      } else if (type === 'boolean') {
        formattedValue = event.target.value === 'true';
      } else if (type === 'date') {
        formattedValue = formatarStringParaData(event.target.value);
      }

      onUpdate(formattedValue);
    }
  };

  const formatValue = (val: any) => {
    if ((type !== 'date') && (typeof val === 'string' || typeof val === 'number')) {
      return String(val);
    } else if (val instanceof Date && type === 'month') {
      return formatarDataParaAnoMes(val);
    } else if (typeof val === 'string' && type === 'date') {
      return formatarDataParaStringYMD(formatarStringParaData(val));
    } else if (typeof val === 'boolean' && type === 'boolean') {
      return val ? 'true' : 'false';
    } else {
      return '';
    }
  };

  return (
    <StyledFieldValue width={width} maxWidth={maxWidth} maxHeight={maxHeight} inline={inline} padding={padding}>
      {description && 
        <Label title={hint}>
          {description}
        </Label>
      }
      {type === 'select' ? (
        <StyledSelect
          value={(typeof value === 'object' && value !== null) ? value.key : String(value)}
          onChange={(event) => handleInputChange(event)}
          disabled={!editable}
          inputWidth={inputWidth}
          inline={inline}
          variant={variant}
        >
          <option value=''>{placeholder || 'Selecione uma opção'}</option>
          {options?.map((option) => (
            <option key={option.key} value={String(option.key)}>
              {`${formatarNumeroComZerosAEsquerda(option.key, 2)} - ${option.value}`}
            </option>          
          ))}
        </StyledSelect>
      ) : type === 'boolean' ? (
        <StyledSelect
          value={formatValue(value)}
          onChange={(event) => handleInputChange(event)}
          disabled={!editable}
          inputWidth={inputWidth}
          inline={inline}
          variant={variant}
        >
          <option value="true">Sim</option>
          <option value="false">Não</option>
        </StyledSelect>
      ) : (
        <>
          {icon && 
            <Icon>
              {icon}
            </Icon>
          }
          <StyledInput
            type={editable ? type : 'string'} 
            readOnly={!editable}
            value={formatValue(value)}
            onChange={(event) => handleInputChange(event)}
            inputWidth={inputWidth}
            inline={inline}
            onKeyDown={onKeyDown}
            placeholder={placeholder}
            variant={variant}
          />
        </>
      )}
    </StyledFieldValue>
  );
};

export default FieldValue;

interface StyledFieldValueProps {
  width?: string;
  maxWidth?: string;
  maxHeight?: string;
  inline?: boolean;
  padding?: string;
}

export const StyledFieldValue = styled.div<StyledFieldValueProps>`
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
