import React from 'react';
import { Despesa } from '../../../types/Despesa';
import FlexBox from '../../flexbox/FlexBox';
import FieldValue from '../../fieldvalue/FieldValue';
import { formatarDataParaString } from '../../../utils/DateUtils';
import { formatarValorParaReal } from '../../../utils/ValorUtils';
import { Categoria } from '../../../types/Categoria';

interface DespesaFormProps {
  despesa: Despesa;
  categorias: Categoria[];
  onUpdate: (despesaAtualizada: Despesa) => void;
}

const DespesaForm: React.FC<DespesaFormProps> = ({ despesa, categorias, onUpdate }) => {

  const handleUpdateCategoria = (value: any) => {
    const categoriaSelecionada = categorias.find(c => c.id === (value as Categoria).id);
    if (categoriaSelecionada) {
      const despesaAtualizada: Despesa = {
        ...despesa!,
        categoria: categoriaSelecionada
      };
      onUpdate(despesaAtualizada);
    }
  };
  
  const handleUpdateDescricao = (value: any) => {
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
            value={formatarDataParaString(despesa.dataLancamento)}
          />
        </FlexBox.Item>
        <FlexBox.Item
          borderRight  
        >
          <FieldValue 
            description='Situação'
            type='string'
            value={despesa.situacao}
          />
        </FlexBox.Item>
        <FlexBox.Item
          borderRight  
        >
          <FieldValue 
            description='Valor Total'
            type='number'
            value={formatarValorParaReal(despesa.valorTotal)}
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
            value={despesa.categoria}
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
            value={despesa.descricao}
            editable={true}
            onUpdate={handleUpdateDescricao}
          />
        </FlexBox.Item>
      </FlexBox>
    </FlexBox>
  )
}

export default DespesaForm;
