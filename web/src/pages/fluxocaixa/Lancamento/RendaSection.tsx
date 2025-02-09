import React from 'react';
import { FieldValue, FlexBox, Panel } from '../../../components';
import { Renda } from '../../../types';
import { formatDateToShortString, formatValueToBRL } from '../../../utils';

interface RendaSectionProps {
  renda: Renda;
}
const RendaSection: React.FC<RendaSectionProps> = ({ renda }) => {
  return (
    <Panel maxWidth='1000px' title='Renda' padding='15px 0 0 0'>
      <FlexBox flexDirection="column">
        <FlexBox flexDirection="row" borderBottom>
          <FlexBox.Item borderRight>
            <FieldValue 
              description='Data Recebimento'
              type='string'
                value={formatDateToShortString(renda.dataRecebimento)}
            />
          </FlexBox.Item>
          <FlexBox.Item >
            <FieldValue 
              description='Categoria'
              type='string'
              value={renda?.categoria?.descricao || ''}
            />
          </FlexBox.Item>
        </FlexBox>
        <FlexBox flexDirection="row">
          <FlexBox.Item >
            <FieldValue 
              description='Valor'
              type='string'
              value={formatValueToBRL(renda.valor)}
            />
          </FlexBox.Item>
        </FlexBox>
      </FlexBox>
    </Panel>
  );
};

export default RendaSection;
