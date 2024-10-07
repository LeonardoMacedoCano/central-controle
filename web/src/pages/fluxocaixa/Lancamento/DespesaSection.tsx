import React from 'react';
import { FieldValue, FlexBox, Panel } from '../../../components';
import { Despesa, getDescricaoDespesaFormaPagamento } from '../../../types';
import { formatDateToShortString, formatValueToBRL } from '../../../utils';

interface DespesaSectionProps {
  despesa: Despesa;
}
const DespesaSection: React.FC<DespesaSectionProps> = ({ despesa }) => {
  return (
    <Panel maxWidth='1000px' title='Despesa' padding='15px 0 0 0'>
      <FlexBox flexDirection="column">
        <FlexBox flexDirection="row" borderBottom>
          <FlexBox.Item borderRight>
            <FieldValue 
              description='Data Vencimento'
              type='string'
                value={formatDateToShortString(despesa.dataVencimento)}
            />
          </FlexBox.Item>
          <FlexBox.Item >
            <FieldValue 
              description='Categoria'
              type='string'
              value={despesa?.categoria?.descricao || ''}
            />
          </FlexBox.Item>
        </FlexBox>
        <FlexBox flexDirection="row" borderBottom>
          <FlexBox.Item borderRight >
            <FieldValue 
              description='Forma Pagamento'
              type='string'
              value={getDescricaoDespesaFormaPagamento(despesa?.formaPagamento)}
            />
          </FlexBox.Item>
          <FlexBox.Item >
            <FieldValue 
              description='Pago'
              type='string'
              value={despesa?.pago ? 'Sim' : 'Não'}
            />
          </FlexBox.Item>
        </FlexBox>
        <FlexBox flexDirection="row">
          <FieldValue 
              description='Valor'
              type='string'
              value={formatValueToBRL(despesa.valor)}
            />
        </FlexBox>
      </FlexBox>
    </Panel>
  );
};

export default DespesaSection;
