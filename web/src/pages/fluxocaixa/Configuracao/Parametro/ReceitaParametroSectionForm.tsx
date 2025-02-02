import React from 'react';
import { Categoria, FluxoCaixaParametro } from '../../../../types';
import { FieldValue, FlexBox } from '../../../../components';

interface props {
  parametros: FluxoCaixaParametro;
  categorias: Categoria[];
  onUpdate: (parametrosAtualizado: FluxoCaixaParametro) => void;
}

const ReceitaParametroSectionForm: React.FC<props> = ({ parametros, categorias, onUpdate }) => {
  const handleUpdateCategoriaGanhosAtivo = (value: any) => {
    const selectedCategoria = categorias.find(c => String(c.id) === String(value)); 
    onUpdate({ ...parametros, receitaCategoriaParaGanhoAtivo: selectedCategoria });
  };

  const handleUpdateCategoriaPadrao = (value: any) => {
    const selectedCategoria = categorias.find(c => String(c.id) === String(value)); 
    onUpdate({ ...parametros, receitaCategoriaPadrao: selectedCategoria });
  };

  return (
    <FlexBox flexDirection="column" borderTop borderBottom borderLeft borderRight>
      <FlexBox flexDirection="row">
        <FlexBox.Item borderBottom>
          <FieldValue 
            description='Categoria Ganhos Ativo'
            hint="Categoria de receita para ganhos dos ativos."
            type='select'
            value={{ key: String(parametros.receitaCategoriaParaGanhoAtivo?.id), value: parametros.receitaCategoriaParaGanhoAtivo?.descricao }}
            editable={true}
            options={categorias.map(categoria => ({ key: String(categoria.id), value: categoria.descricao }))}
            onUpdate={handleUpdateCategoriaGanhosAtivo}
          />
        </FlexBox.Item>
      </FlexBox>
      <FlexBox flexDirection="row">
        <FlexBox.Item>
          <FieldValue 
            description='Categoria Padrão'
            hint="Categoria de receita padrão."
            type='select'
            value={{ key: String(parametros.receitaCategoriaPadrao?.id), value: parametros.receitaCategoriaPadrao?.descricao }}
            editable={true}
            options={categorias.map(categoria => ({ key: String(categoria.id), value: categoria.descricao }))}
            onUpdate={handleUpdateCategoriaPadrao}
          />
        </FlexBox.Item>
      </FlexBox>
    </FlexBox>
  );
};

export default ReceitaParametroSectionForm;