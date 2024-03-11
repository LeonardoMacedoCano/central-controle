import React from 'react';
import * as C from './styles';
import { formatarDataParaAnoMes, formatarDataParaString } from '../../utils/DateUtils';

type FieldValueProps = {
  description: string;
  type: 'string' | 'number' | 'boolean' | 'date' | 'month';
  value: string | number | boolean | Date;
  editable?: boolean;
  width?: string;
  maxWidth?: string;
  onUpdate?: (value: string | number | boolean | Date) => void;
};

class FieldValue extends React.Component<FieldValueProps> {
  handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { onUpdate } = this.props;
    if (onUpdate) {
      onUpdate(event.target.value);
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
    const { description, type, value, editable, width, maxWidth } = this.props;
    return (
      <C.FieldValue width={width} maxWidth={maxWidth}>
        <C.Label>
          {description}
        </C.Label>
        <C.Input
          type={editable ? type : 'string'} 
          readOnly={!editable}
          value={this.formatValue(value)}
          onChange={this.handleInputChange}
        />
      </C.FieldValue>
    );
  }
};

export default FieldValue;
