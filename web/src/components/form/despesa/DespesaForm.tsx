import React from 'react';
import { Despesa } from '../../../types/Despesa';
import { Categoria } from '../../../types/Categoria';
import FlexBox from '../../flexbox/FlexBox';
import FieldValue from '../../fieldvalue/FieldValue';
import { formatarDataParaString } from '../../../utils/DateUtils';
import { formatarValorParaReal } from '../../../utils/ValorUtils';

interface DespesaFormProps {
  despesa: Despesa;
  categorias: Categoria[];
  onUpdate: (despesaAtualizada: Despesa) => void;
}

const DespesaForm: React.FC<DespesaFormProps> = ({ despesa, categorias, onUpdate }) => {
  const updateDespesa = (updatedFields: Partial<Despesa>) => {
    const despesaAtualizada: Despesa = {
      ...despesa!,
      ...updatedFields
    };
    onUpdate(despesaAtualizada);
  };

  const handleUpdateCategoria = (value: any) => {
    const categoriaId = String(value);
    const categoriaSelecionada = categorias.find(c => String(c.id) === categoriaId); 
    if (categoriaSelecionada) {
      updateDespesa({ categoria: categoriaSelecionada });
    }
  };

  const handleUpdateDescricao = (value: any) => {
    if (typeof value === 'string') {
      updateDespesa({ descricao: value });
    }
  };
  
  return (
    <FlexBox flexDirection="column">
      <FlexBox flexDirection="row">
        <FlexBox.Item borderRight>
          <FieldValue 
            description='Data Lançamento'
            type='string'
            value={formatarDataParaString(despesa.dataLancamento)}
          />
        </FlexBox.Item>
        <FlexBox.Item borderRight>
          <FieldValue 
            description='Situação'
            type='string'
            value={despesa.situacao}
          />
        </FlexBox.Item>
        <FlexBox.Item>
          <FieldValue 
            description='Valor Total'
            type='number'
            value={formatarValorParaReal(despesa.valorTotal)}
          />
        </FlexBox.Item>
      </FlexBox>
      <FlexBox flexDirection="row">
        <FlexBox.Item borderTop borderRight>
          <FieldValue 
            description='Categoria'
            type='select'
            value={{ key: despesa.categoria.id, value: despesa.categoria.descricao }}
            editable={true}
            options={categorias.map(categoria => ({ key: categoria.id, value: categoria.descricao }))}
            onUpdate={handleUpdateCategoria}
          />
        </FlexBox.Item>
        <FlexBox.Item borderTop>
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
  );
}

export default DespesaForm;
