import React from 'react';
import * as C from './styles';
import { formatarDataParaAnoMes, formatarDataParaStringYMD, formatarStringParaData } from '../../utils/DateUtils';
import { formatarNumeroComZerosAEsquerda } from '../../utils/ValorUtils';
import { SelectValue } from '../../types/SelectValue';

type FieldValueProps = {
  type: 'string' | 'number' | 'boolean' | 'date' | 'month' | 'select';
  value: string | number | boolean | SelectValue;
  description?: string;
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
  description,
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
    <C.FieldValue width={width} maxWidth={maxWidth} maxHeight={maxHeight} inline={inline} padding={padding}>
      {description && 
        <C.Label>
          {description}
        </C.Label>
      }
      {type === 'select' ? (
        <C.StyledSelect
          value={(typeof value === 'object' && value !== null) ? value.key : String(value)}
          onChange={(event) => handleInputChange(event)}
          disabled={!editable}
          inputWidth={inputWidth}
          inline={inline}
        >
          <option value=''>{placeholder || 'Selecione uma opção'}</option>
          {options?.map((option) => (
            <option key={option.key} value={String(option.key)}>
              {`${formatarNumeroComZerosAEsquerda(option.key, 2)} - ${option.value}`}
            </option>          
          ))}
        </C.StyledSelect>
      ) : type === 'boolean' ? (
        <C.StyledSelect
          value={formatValue(value)}
          onChange={(event) => handleInputChange(event)}
          disabled={!editable}
          inputWidth={inputWidth}
          inline={inline}
        >
          <option value="true">Sim</option>
          <option value="false">Não</option>
        </C.StyledSelect>
      ) : (
        <>
          {icon && 
            <C.Icon>
              {icon}
            </C.Icon>
          }
          <C.StyledInput
            type={editable ? type : 'string'} 
            readOnly={!editable}
            value={formatValue(value)}
            onChange={(event) => handleInputChange(event)}
            inputWidth={inputWidth}
            inline={inline}
            onKeyDown={onKeyDown}
            placeholder={placeholder}
          />
        </>
      )}
    </C.FieldValue>
  );
};

export default FieldValue;
