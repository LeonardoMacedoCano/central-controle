import React from 'react';
import { FieldValue, FlexBox, Panel } from '../../../components';
import { Receita } from '../../../types';
import { formatDateToShortString, formatValueToBRL } from '../../../utils';

interface ReceitaSectionProps {
  receita: Receita;
}
const ReceitaSection: React.FC<ReceitaSectionProps> = ({ receita }) => {
  return (
    <Panel maxWidth='1000px' title='Receita' padding='15px 0 0 0'>
      <FlexBox flexDirection="column">
        <FlexBox flexDirection="row" borderBottom>
          <FlexBox.Item borderRight>
            <FieldValue 
              description='Data Recebimento'
              type='string'
                value={formatDateToShortString(receita.dataRecebimento)}
            />
          </FlexBox.Item>
          <FlexBox.Item >
            <FieldValue 
              description='Categoria'
              type='string'
              value={receita?.categoria?.descricao || ''}
            />
          </FlexBox.Item>
        </FlexBox>
        <FlexBox flexDirection="row">
          <FlexBox.Item >
            <FieldValue 
              description='Valor'
              type='string'
              value={formatValueToBRL(receita.valor)}
            />
          </FlexBox.Item>
        </FlexBox>
      </FlexBox>
    </Panel>
  );
};

export default ReceitaSection;
