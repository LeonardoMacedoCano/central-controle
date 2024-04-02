import React from 'react';
import * as C from './styles';
import { formatarDataParaAnoMes, formatarDataParaString, formatarStringParaData } from '../../utils/DateUtils';

type FieldValueProps = {
  type: 'string' | 'number' | 'boolean' | 'date' | 'month';
  value: string | number | boolean | Date;
  description?: string;
  editable?: boolean;
  width?: string;
  maxWidth?: string;
  maxHeight?: string;
  minValue?: number;
  maxValue?: number;
  inputWidth?: string;
  inline?: boolean;
  onUpdate?: (value: string | number | boolean | Date) => void;
};

class FieldValue extends React.Component<FieldValueProps> {
  handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { onUpdate, type, minValue, maxValue } = this.props;
  
    if (onUpdate) {
      let formattedValue: string | number | boolean | Date = event.target.value;
  
      if (type === 'number' && typeof formattedValue === 'number') {
        formattedValue = parseFloat(event.target.value);
  
        if (minValue && formattedValue < minValue) {
          formattedValue = minValue;
        } else if (maxValue && formattedValue > maxValue) {
          formattedValue = maxValue;
        }
      } else if (typeof formattedValue === 'boolean' && type === 'boolean') {
        formattedValue = event.target.value === 'true';
      } else if (type === 'date') {
        formattedValue = formatarStringParaData(event.target.value);
      }

      onUpdate(formattedValue);
    }
  };

  formatValue = (val: string | number | boolean | Date) => {
    const { type } = this.props;
    if (typeof val === 'string' || typeof val === 'number' || typeof val === 'boolean') {
      return String(val);
    } else if (val instanceof Date && type === 'month') {
      return formatarDataParaAnoMes(val);
    } else {
      return formatarDataParaString(val);
    }
  };

  render() {
    const { description, type, value, editable, width, maxWidth, maxHeight, inline, inputWidth } = this.props;
    return (
      <C.FieldValue width={width} maxWidth={maxWidth} maxHeight={maxHeight} inline={inline}>
        {description && 
          <C.Label>
            {description}
          </C.Label>
        }
        <C.Input
          type={editable ? type : 'string'} 
          readOnly={!editable}
          value={this.formatValue(value)}
          onChange={this.handleInputChange}
          inputWidth={inputWidth}
          inline={inline}
        />
      </C.FieldValue>
    );
  }
};

export default FieldValue;
