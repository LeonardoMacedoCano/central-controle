import React, { useContext, useEffect, useState } from 'react';
import { AuthContext } from '../../contexts/auth/AuthContext';
import { useMessage } from '../../contexts/message/ContextMessageProvider';
import ParcelaService from '../../service/fluxocaixa/ParcelaService';
import { formatDateToYMDString } from '../../utils/DateUtils';
import { FormaPagamento } from '../../types/fluxocaixa/FormaPagamento';
import { Parcela } from '../../types/fluxocaixa/Parcela';
import FlexBox from '../../components/flexbox/FlexBox';
import FieldValue from '../../components/fieldvalue/FieldValue';

interface ParcelaFormProps {
  parcela: Parcela;
  onUpdate: (parcelaAtualizada: Parcela) => void;
}

const ParcelaForm: React.FC<ParcelaFormProps> = ({ parcela, onUpdate }) => {
  const [formasPagamento, setFormasPagamento] = useState<FormaPagamento[]>([]);

  const auth = useContext(AuthContext);
  const message = useMessage();
  const parcelaService = ParcelaService();

  useEffect(() => {
    const carregarCategoriasDespesa = async () => {
      if (!auth.usuario?.token) return;
  
      try {
        const result = await parcelaService.getTodasFormaPagamento(auth.usuario?.token);
        setFormasPagamento(result || []);
      } catch (error) {
        message.showErrorWithLog('Erro ao carregar as formas de pagamento.', error);
      }
    };

    carregarCategoriasDespesa();
  }, []);

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
            value={formatDateToYMDString(parcela.dataVencimento)}
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
