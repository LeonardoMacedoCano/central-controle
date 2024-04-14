import React from 'react';
import * as C from './styles';
import { formatarDataParaAnoMes, formatarDataParaStringYMD, formatarStringParaData } from '../../utils/DateUtils';
import { Categoria } from '../../types/Categoria';

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

class FieldValue extends React.Component<FieldValueProps> {

  handleInputChange = (event: React.ChangeEvent<HTMLInputElement> | React.ChangeEvent<HTMLSelectElement>) => {
    const { onUpdate, type, minValue, maxValue } = this.props;
  
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

  handleCategoriaChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const selectedId = parseInt(event.target.value, 10);
    const selectedCategoria = this.getSelectedCategoria(selectedId);

    if (this.props.onUpdate) {
      this.props.onUpdate(selectedCategoria || '');
    }
  };

  formatValue = (val: any) => {
    const { type } = this.props;

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

  getSelectedCategoria = (selectedId: number): Categoria | null => {
    if (selectedId > 0) {
      return this.props.categorias?.find(c => c.id === selectedId) || null;
    } else if (this.props.value instanceof Object && 'id' in this.props.value) {
      return this.props.value as Categoria;
    } else {
      return null
    }
  };

  render() {
    const { 
      description, 
      type, 
      value, 
      editable, 
      width, 
      maxWidth, 
      maxHeight, 
      inline, 
      inputWidth, 
      categorias, 
      icon, 
      padding, 
      placeholder, 
      onKeyDown 
    } = this.props;
    const selectedCategoria = (type === 'categoria' ? this.getSelectedCategoria(parseInt(value as string, 10)) : undefined);

    return (
      <C.FieldValue width={width} maxWidth={maxWidth} maxHeight={maxHeight} inline={inline} padding={padding}>
        {description && 
          <C.Label>
            {description}
          </C.Label>
        }
        {type === 'categoria' ? (
          <C.Select
            value={selectedCategoria ? selectedCategoria.id.toString() : ''}
            onChange={this.handleCategoriaChange}
            disabled={!editable}
            inputWidth={inputWidth}
            inline={inline}
          >
            <option value="">Selecione uma categoria</option>
            {categorias?.map((categoria) => (
              <option key={categoria.id} value={categoria.id.toString()}>
                {`${categoria.id} - ${categoria.descricao}`}
              </option>
            ))}
          </C.Select>
        ) : type === 'boolean' ? (
          <C.Select
            value={this.formatValue(value)}
            onChange={this.handleInputChange}
            disabled={!editable}
            inputWidth={inputWidth}
            inline={inline}
          >
            <option value="true">Sim</option>
            <option value="false">Não</option>
          </C.Select>
        ) : (
          <>
            {icon && 
              <C.Icon>
                {icon}
              </C.Icon>
            }
            <C.Input
              type={editable ? type : 'string'} 
              readOnly={!editable}
              value={this.formatValue(value)}
              onChange={this.handleInputChange}
              inputWidth={inputWidth}
              inline={inline}
              onKeyDown={onKeyDown}
              placeholder={placeholder}
            />
          </>
        )}
      </C.FieldValue>
    );
  }
}

export default FieldValue;
