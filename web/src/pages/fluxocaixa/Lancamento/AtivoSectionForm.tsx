import React from 'react';
import { FieldValue, FlexBox, Panel } from '../../../components';
import { Ativo, ativoCategoriaOptions, ativoOperacaoOptions, getAtivoCategoriaByCodigo, getAtivoOperacaoByCodigo, getCodigoAtivoCategoria, getCodigoAtivoOperacao, getDescricaoAtivoCategoria, getDescricaoAtivoOperacao } from '../../../types';
import { formatDateToYMDString } from '../../../utils';

interface AtivoSectionFormProps {
  ativo: Ativo;
  onUpdate: (updatedAtivo: Ativo) => void;
}

const AtivoSectionForm: React.FC<AtivoSectionFormProps> = ({ ativo, onUpdate }) => {
  const updateAtivo = (updatedFields: Partial<Ativo>) => {
    const updatedAtivo: Ativo = {
      ...ativo!,
      ...updatedFields
    };
    onUpdate(updatedAtivo);
  };

  const handleUpdateDataMovimento = (value: any) => {
    if (value instanceof Date) {
      updateAtivo({ dataMovimento: value });
    }
  };

  const handleUpdateCategoria = (value: any) => {
    const selectedCategoria = getAtivoCategoriaByCodigo(value); 
    updateAtivo({ categoria: selectedCategoria });
  };

  const handleUpdateValor = (value: any) => {
    updateAtivo({ valor: value });
  };
  
  const handleUpdateOperacao = (value: any) => {
    const selectedOperacao = getAtivoOperacaoByCodigo(value); 
    updateAtivo({ operacao: selectedOperacao });
  };

  return (
    <Panel maxWidth='1000px' title='Ativo' padding='15px 0 0 0'>
      <FlexBox flexDirection="column">
        <FlexBox flexDirection="row" borderBottom>
          <FlexBox.Item borderRight>
            <FieldValue 
              description='Data Movimentação'
              type='date'
              value={formatDateToYMDString(ativo?.dataMovimento)}
              editable={true}
              onUpdate={handleUpdateDataMovimento}
            />
          </FlexBox.Item>
          <FlexBox.Item >
            <FieldValue 
              description='Categoria'
              type='select'
              value={{ key: getCodigoAtivoCategoria(ativo?.categoria), value: getDescricaoAtivoCategoria(ativo?.categoria) }}
              editable={true}
              options={ativoCategoriaOptions}
              onUpdate={handleUpdateCategoria}
            />
          </FlexBox.Item>
        </FlexBox>
        <FlexBox flexDirection="row">
          <FlexBox.Item borderRight >
            <FieldValue 
              description='Operação'
              type='select'
              value={{ key: getCodigoAtivoOperacao(ativo?.operacao), value: getDescricaoAtivoOperacao(ativo?.operacao) }}
              editable={true}
              options={ativoOperacaoOptions}
              onUpdate={handleUpdateOperacao}
            />
          </FlexBox.Item>
          <FlexBox.Item >
            <FieldValue 
              description='Valor'
              type='number'
              value={ativo.valor}
              editable={true}
              onUpdate={handleUpdateValor}
            />
          </FlexBox.Item>
        </FlexBox>
      </FlexBox>
    </Panel>
  );
};

export default AtivoSectionForm;
