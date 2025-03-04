import React from 'react';
import {
  Categoria,
  FluxoCaixaParametro,
} from '../../../../types';
import {
  FieldValue,
  FlexBox,
} from '../../../../components';

interface props {
  parametros: FluxoCaixaParametro;
  categorias: Categoria[];
  onUpdate: (parametrosAtualizado: FluxoCaixaParametro) => void;
}

const DespesaParametroSectionForm: React.FC<props> = ({ parametros, categorias, onUpdate }) => {
  const handleMetaLimiteDespesaMensal = (value: any) => {
    onUpdate({ ...parametros, metaLimiteDespesaMensal: value });
  };

  const handleUpdateCategoriaPadrao = (value: any) => {
    const selectedCategoria = categorias.find(c => String(c.id) === String(value)); 
    onUpdate({ ...parametros, despesaCategoriaPadrao: selectedCategoria });
  };

  return (
    <FlexBox flexDirection="column">
      <FlexBox flexDirection="row">
        <FlexBox.Item borderBottom>
          <FieldValue
            description="Valor Teto Meta Mensal"
            hint="Meta máxima para o total de despesas em um mês."
            type="number"
            value={parametros.metaLimiteDespesaMensal}
            editable
            minValue={0}
            onUpdate={handleMetaLimiteDespesaMensal}
          />
        </FlexBox.Item>
      </FlexBox>

      <FlexBox flexDirection="row">
        <FlexBox.Item>
          <FieldValue 
            description='Categoria Padrão'
            type='select'
            value={{ key: String(parametros.despesaCategoriaPadrao?.id), value: parametros.despesaCategoriaPadrao?.descricao }}
            editable={true}
            options={categorias.map(categoria => ({ key: String(categoria.id), value: categoria.descricao }))}
            onUpdate={handleUpdateCategoriaPadrao}
          />
        </FlexBox.Item>
      </FlexBox>
    </FlexBox>
  );
};

export default DespesaParametroSectionForm;