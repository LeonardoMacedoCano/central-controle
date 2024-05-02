import React from 'react';
import * as C from './styles';
import { formatarDataParaAnoMes, formatarDataParaStringYMD, formatarStringParaData } from '../../utils/DateUtils';
import { Categoria } from '../../types/Categoria';
import { formatarNumeroComZerosAEsquerda } from '../../utils/ValorUtils';

type FieldValueProps = {
  type: 'string' | 'number' | 'boolean' | 'date' | 'month' | 'categoria';
  value: string | number | boolean | Categoria;
  description?: string;
  editable?: boolean;
  width?: string;
  maxWidth?: string;
  maxHeight?: string;
  minValue?: number;
  maxValue?: number;
  inputWidth?: string;
  inline?: boolean;
  categorias?: Categoria[];
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
  categorias,
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
        if (minValue && formattedValue < minValue) {
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

  const handleCategoriaChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const selectedId = parseInt(event.target.value, 10);
    const selectedCategoria = getSelectedCategoria(selectedId);

    if (onUpdate) {
      onUpdate(selectedCategoria || '');
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

  const getSelectedCategoria = (selectedId: number): Categoria | null => {
    if (selectedId > 0) {
      return categorias?.find(c => c.id === selectedId) || null;
    } else if (value instanceof Object && 'id' in value) {
      return value as Categoria;
    } else {
      return null;
    }
  };

  const selectedCategoria = (type === 'categoria' ? getSelectedCategoria(parseInt(value as string, 10)) : undefined);

  return (
    <C.FieldValue width={width} maxWidth={maxWidth} maxHeight={maxHeight} inline={inline} padding={padding}>
      {description && 
        <C.Label>
          {description}
        </C.Label>
      }
      {type === 'categoria' ? (
        <C.StyledSelect
          value={selectedCategoria ? selectedCategoria.id.toString() : ''}
          onChange={handleCategoriaChange}
          disabled={!editable}
          inputWidth={inputWidth}
          inline={inline}
        >
          <option value="">Selecione uma categoria</option>
          {categorias?.map((categoria) => (
            <option key={categoria.id} value={categoria.id.toString()}>
              {`${formatarNumeroComZerosAEsquerda(categoria.id, 2)} - ${categoria.descricao}`}
            </option>
          ))}
        </C.StyledSelect>
      ) : type === 'boolean' ? (
        <C.StyledSelect
          value={formatValue(value)}
          onChange={handleInputChange}
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
            onChange={handleInputChange}
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
