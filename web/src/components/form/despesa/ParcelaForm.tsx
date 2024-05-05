import React from 'react';
import { Parcela } from '../../../types/Parcela';
import FlexBox from '../../flexbox/FlexBox';
import FieldValue from '../../fieldvalue/FieldValue';
import { formatarDataParaStringYMD } from '../../../utils/DateUtils';
import { FormaPagamento } from '../../../types/FormaPagamento';

interface ParcelaFormProps {
  parcela: Parcela;
  formasPagamento: FormaPagamento[];
  onUpdate: (parcelaAtualizada: Parcela) => void;
}

const ParcelaForm: React.FC<ParcelaFormProps> = ({ parcela, formasPagamento, onUpdate }) => {
  const updateParcela = (updatedFields: Partial<Parcela>) => {
    const parcelaAtualizada: Parcela = {
      ...parcela!,
      ...updatedFields
    };
    onUpdate(parcelaAtualizada);
  };

  const handleUpdateDataVencimento = (value: any) => {
    if (value instanceof Date) {
      updateParcela({ dataVencimento: value });
    }
  };
  
  const handleUpdateValor = (value: any) => {
    if (typeof value === 'number') {
      updateParcela({ valor: value });
    }
  };

  const handleUpdatePago = (value: any) => {
    if (typeof value === 'boolean') {
      updateParcela({ pago: value });
    }
  };

  const handleFormaPagamento = (value: any) => {
    const formaPagamentoId = String(value);
    const formaPagamentoSelecionado = formaPagamentoId === '' ? null : formasPagamento.find(c => String(c.id) === formaPagamentoId); 
    updateParcela({ formaPagamento: formaPagamentoSelecionado });
  };

  return (
    <FlexBox flexDirection="column">
      <FlexBox flexDirection="row">
        <FlexBox.Item borderBottom>
          <FieldValue 
            description='Numero'
            type='number'
            value={parcela.numero}
          />
        </FlexBox.Item>
      </FlexBox>
      <FlexBox flexDirection="row">
        <FlexBox.Item borderRight>
          <FieldValue 
            description='Data Vencimento'
            type='date'
            value={formatarDataParaStringYMD(parcela.dataVencimento)}
            editable={true}
            onUpdate={handleUpdateDataVencimento}
          />
        </FlexBox.Item>
        <FlexBox.Item >
          <FieldValue 
            description='Valor'
            type='number'
            value={parcela.valor}
            editable={true}
            onUpdate={handleUpdateValor}
          />
        </FlexBox.Item>
      </FlexBox>
      <FlexBox flexDirection="row">
        <FlexBox.Item borderTop borderRight>
          <FieldValue 
            description='Pago'
            type='boolean'
            value={parcela.pago}
            editable={true}
            onUpdate={handleUpdatePago}
          />
        </FlexBox.Item>
        <FlexBox.Item borderTop>
          <FieldValue 
            description='Forma Pagamento'
            type='select'
            value={{ key: parcela.formaPagamento?.id || 0, value: parcela.formaPagamento?.descricao || ''}}
            editable={true}
            options={formasPagamento.map(formaPagamento => ({ key: formaPagamento.id, value: formaPagamento.descricao }))}
            onUpdate={handleFormaPagamento}
          />
        </FlexBox.Item>
      </FlexBox>
    </FlexBox>
  );
}

export default ParcelaForm;
