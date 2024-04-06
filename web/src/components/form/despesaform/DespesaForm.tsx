import React from 'react';
import { Despesa } from '../../../types/Despesa';
import FlexBox from '../../flexbox/FlexBox';
import FieldValue from '../../fieldvalue/FieldValue';
import { formatarDataParaString } from '../../../utils/DateUtils';
import { formatarValorParaReal } from '../../../utils/ValorUtils';
import { Categoria } from '../../../types/Categoria';

interface DespesaFormProps {
  despesa: Despesa | null;
  categorias: Categoria[] | [];
  onAdd: (novaDespesa: Despesa) => void;
  onUpdate: (despesaAtualizada: Despesa) => void;
}

const DespesaForm: React.FC<DespesaFormProps> = ({ despesa, categorias, onAdd, onUpdate }) => {
  const handleUpdateDataLancamento = (value: string | number | boolean | Date | Categoria) => {
    if (value instanceof Date) {
      const despesaAtualizada: Despesa = {
        ...despesa!,
        dataLancamento: value
      };
      onUpdate(despesaAtualizada);
    }
  };

  const handleUpdateCategoria = (value: string | number | boolean | Date | Categoria) => {
    if (value instanceof Object && 'id' in value) {
      const despesaAtualizada: Despesa = {
        ...despesa!,
        categoria: value
      };
      onUpdate(despesaAtualizada);
    }
  };
  
  const handleUpdateDescricao = (value: string | number | boolean | Date | Categoria) => {
    if (typeof value === 'string') {
      const despesaAtualizada: Despesa = {
        ...despesa!,
        descricao: value
      };
      onUpdate(despesaAtualizada);
    }
  };

  return (
    <FlexBox 
      flexDirection="column"
    >
      <FlexBox 
        flexDirection="row"
      >
        <FlexBox.Item
          borderRight  
        >
          <FieldValue 
            description='Data Lançamento'
            type='string'
            value={formatarDataParaString(despesa?.dataLancamento)}
            onUpdate={handleUpdateDataLancamento}
          />
        </FlexBox.Item>
        <FlexBox.Item
          borderRight  
        >
          <FieldValue 
            description='Situação'
            type='string'
            value={despesa?.situacao || ''}
          />
        </FlexBox.Item>
        <FlexBox.Item
          borderRight  
        >
          <FieldValue 
            description='Valor Total'
            type='number'
            value={formatarValorParaReal(despesa?.valorTotal || 0)}
          />
        </FlexBox.Item>
      </FlexBox>

      <FlexBox 
        flexDirection="row"
      >
        <FlexBox.Item
          borderTop 
          borderRight 
        >
          <FieldValue 
            description='Categoria'
            type='categoria'
            value={despesa?.categoria || ''}
            editable={true}
            categorias={categorias}
            onUpdate={handleUpdateCategoria}
          />
        </FlexBox.Item>
        <FlexBox.Item
          borderTop
          borderRight  
        >
          <FieldValue 
            description='Descrição'
            type='string'
            value={despesa?.descricao || ''}
            editable={true}
            onUpdate={handleUpdateDescricao}
          />
        </FlexBox.Item>
      </FlexBox>
      
    </FlexBox>
  )
}

export default DespesaForm;
