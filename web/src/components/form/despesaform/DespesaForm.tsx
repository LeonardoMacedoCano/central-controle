import React from 'react';
import { Despesa } from '../../../types/Despesa';
import FlexBox from '../../flexbox/FlexBox';
import FieldValue from '../../fieldvalue/FieldValue';
import { formatarDataParaStringYMD } from '../../../utils/DateUtils';

interface DespesaFormProps {
  despesa: Despesa | null;
  onAdd: (novaDespesa: Despesa) => void;
  onUpdate: (despesaAtualizada: Despesa) => void;
}

const DespesaForm: React.FC<DespesaFormProps> = ({ despesa, onAdd, onUpdate }) => {
  const handleUpdateDataLancamento = (value: string | number | boolean | Date) => {
    if (value instanceof Date) {
      const despesaAtualizada: Despesa = {
        ...despesa!,
        dataLancamento: value
      };
      onUpdate(despesaAtualizada);
    }
  };
  
  const handleUpdateDescricao = (value: string | number | boolean | Date) => {
    if (typeof value === 'string') {
      const despesaAtualizada: Despesa = {
        ...despesa!,
        descricao: value
      };
      onUpdate(despesaAtualizada);
    }
  };

  return (
    <FlexBox>
      <FlexBox.Item>
        <FieldValue 
          description='Data Lançamento'
          type='date'
          value={formatarDataParaStringYMD(despesa?.dataLancamento) || ''}
          editable={true}
          onUpdate={handleUpdateDataLancamento}
        />
        <FieldValue 
          description='Descrição'
          type='string'
          value={despesa?.descricao || ''}
          editable={true}
          onUpdate={handleUpdateDescricao}
        />
      </FlexBox.Item>
    </FlexBox>
  )
}

export default DespesaForm;
