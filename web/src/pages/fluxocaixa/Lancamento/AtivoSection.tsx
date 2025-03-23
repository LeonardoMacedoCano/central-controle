import React from 'react';
import { FieldValue, FlexBox, Panel } from '../../../components';
import { Ativo, getDescricaoAtivoCategoria, getDescricaoAtivoOperacao } from '../../../types';
import { formatDateToShortString, formatNumberWithTrailingZeros, formatValueToBRL } from '../../../utils';

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
        <FlexBox flexDirection="row" borderBottom>
          <FlexBox.Item borderRight >
            <FieldValue 
              description='Ticker'
              type='string'
              value={ativo?.ticker || ''}
            />
          </FlexBox.Item>
          <FlexBox.Item >
            <FieldValue 
                description='Operação'
                type='string'
                value={getDescricaoAtivoOperacao(ativo?.operacao)}
              />
          </FlexBox.Item>
        </FlexBox>
        <FlexBox flexDirection="row">
          <FlexBox.Item borderRight >
            <FieldValue 
              description='Preço Unitário'
              type='string'
              value={formatValueToBRL(ativo.precoUnitario)}
            />
          </FlexBox.Item>
          <FlexBox.Item >
            <FieldValue 
                description='Quantidade'
                type='string'
                value={formatNumberWithTrailingZeros(ativo.quantidade, 6)}
              />
          </FlexBox.Item>
        </FlexBox>
      </FlexBox>
    </Panel>
  );
};

export default AtivoSection;
