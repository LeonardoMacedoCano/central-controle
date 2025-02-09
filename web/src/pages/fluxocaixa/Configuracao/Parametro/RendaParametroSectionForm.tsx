import React from 'react';
import { Categoria, FluxoCaixaParametro } from '../../../../types';
import { FieldValue, FlexBox } from '../../../../components';

interface props {
  parametros: FluxoCaixaParametro;
  categorias: Categoria[];
  onUpdate: (parametrosAtualizado: FluxoCaixaParametro) => void;
}

const RendaParametroSectionForm: React.FC<props> = ({ parametros, categorias, onUpdate }) => {

  const handleUpdateCategoriaGanhosAtivo = (value: any) => {
    const selectedCategoria = categorias.find(c => String(c.id) === String(value)); 
    onUpdate({ ...parametros, rendaPassivaCategoria: selectedCategoria });
  };

  const handleUpdateCategoriaPadrao = (value: any) => {
    const selectedCategoria = categorias.find(c => String(c.id) === String(value)); 
    onUpdate({ ...parametros, rendaCategoriaPadrao: selectedCategoria });
  };

  return (
    <FlexBox flexDirection="column" borderTop borderBottom borderLeft borderRight>
      <FlexBox flexDirection="row">
        <FlexBox.Item borderBottom>
          <FieldValue 
            description='Categoria Padrão'
            hint="Categoria de renda padrão."
            type='select'
            value={{ key: String(parametros.rendaCategoriaPadrao?.id), value: parametros.rendaCategoriaPadrao?.descricao }}
            editable={true}
            options={categorias.map(categoria => ({ key: String(categoria.id), value: categoria.descricao }))}
            onUpdate={handleUpdateCategoriaPadrao}
          />
        </FlexBox.Item>
      </FlexBox>
      <FlexBox flexDirection="row">
        <FlexBox.Item>
          <FieldValue 
            description='Categoria Renda Passiva'
            hint="Categoria de renda passiva."
            type='select'
            value={{ key: String(parametros.rendaPassivaCategoria?.id), value: parametros.rendaPassivaCategoria?.descricao }}
            editable={true}
            options={categorias.map(categoria => ({ key: String(categoria.id), value: categoria.descricao }))}
            onUpdate={handleUpdateCategoriaGanhosAtivo}
          />
        </FlexBox.Item>
      </FlexBox>
    </FlexBox>
  );
};

export default RendaParametroSectionForm;