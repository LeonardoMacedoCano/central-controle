import React from 'react';
import { FieldValue, FlexBox, Panel } from '../../../components';
import { Ativo, getDescricaoAtivoCategoria, getDescricaoAtivoOperacao } from '../../../types';
import { formatDateToShortString, formatValueToBRL } from '../../../utils';

interface AtivoSectionProps {
  ativo: Ativo;
}
const AtivoSection: React.FC<AtivoSectionProps> = ({ ativo }) => {
  return (
    <Panel maxWidth='1000px' title='Ativo' padding='15px 0 0 0'>
      <FlexBox flexDirection="column">
        <FlexBox flexDirection="row" borderBottom>
          <FlexBox.Item borderRight>
            <FieldValue 
              description='Data Movimentação'
              type='string'
              value={formatDateToShortString(ativo.dataMovimento)}
            />
          </FlexBox.Item>
          <FlexBox.Item >
            <FieldValue 
              description='Categoria'
              type='string'
              value={getDescricaoAtivoCategoria(ativo?.categoria) || ''}
            />
          </FlexBox.Item>
        </FlexBox>
        <FlexBox flexDirection="row" >
          <FlexBox.Item borderRight >
            <FieldValue 
                description='Operação'
                type='string'
                value={getDescricaoAtivoOperacao(ativo?.operacao)}
              />
          </FlexBox.Item>
          <FlexBox.Item >
            <FieldValue 
              description='Valor'
              type='string'
              value={formatValueToBRL(ativo.valor)}
            />
          </FlexBox.Item>
        </FlexBox>
      </FlexBox>
    </Panel>
  );
};

export default AtivoSection;
