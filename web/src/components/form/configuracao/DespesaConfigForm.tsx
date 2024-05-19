import React, { useContext, useEffect, useState } from 'react';
import { AuthContext } from '../../../contexts/auth/AuthContext';
import { useMessage } from '../../../contexts/message/ContextMessageProvider';
import ParcelaService from '../../../service/ParcelaService';
import { UsuarioConfig } from '../../../types/UsuarioConfig';
import { FormaPagamento } from '../../../types/FormaPagamento';
import FlexBox from '../../flexbox/FlexBox';
import FieldValue from '../../fieldvalue/FieldValue';

interface DespesaConfigFormProps {
  usuarioConfig: UsuarioConfig;
  onUpdate: (usuarioConfigAtualizado: UsuarioConfig) => void;
}

const DespesaConfigForm: React.FC<DespesaConfigFormProps> = ({ usuarioConfig, onUpdate }) => {
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

  const updateUsuarioConfig = (updatedFields: Partial<UsuarioConfig>) => {
    const usuarioConfigAtualizado: UsuarioConfig = {
      ...usuarioConfig!,
      ...updatedFields
    };
    onUpdate(usuarioConfigAtualizado);
  };

  const handleNumeroMaxItemPagina = (value: any) => {
    if (typeof value === 'number') {
      updateUsuarioConfig({ despesaNumeroMaxItemPagina: value });
    }
  };

  const handleValorMetaMensal = (value: any) => {
    if (typeof value === 'number') {
      updateUsuarioConfig({ despesaValorMetaMensal: value });
    }
  };

  const handleDiaPadraoVencimento = (value: any) => {
    if (typeof value === 'number') {
      updateUsuarioConfig({ despesaDiaPadraoVencimento: value });
    }
  };

  const handleFormaPagamento = (value: any) => {
    const formaPagamentoId = String(value);
    const formaPagamentoSelecionado = formaPagamentoId === '' ? null : formasPagamento.find(c => String(c.id) === formaPagamentoId); 
    updateUsuarioConfig({ despesaFormaPagamentoPadrao: formaPagamentoSelecionado });
  };

  return (
    <FlexBox flexDirection="column">
      <FlexBox flexDirection="row">
        <FlexBox.Item borderBottom>
          <FieldValue 
            description='Numero Max. Item Pagina'
            type='number'
            value={usuarioConfig.despesaNumeroMaxItemPagina}
            minValue={1}
            editable={true}
            onUpdate={handleNumeroMaxItemPagina}
          />
        </FlexBox.Item>
      </FlexBox>
      <FlexBox flexDirection="row">
        <FlexBox.Item borderBottom>
          <FieldValue 
            description='Valor Meta Mensal'
            type='number'
            value={usuarioConfig.despesaValorMetaMensal}
            editable={true}
            minValue={0}
            onUpdate={handleValorMetaMensal}
          />
        </FlexBox.Item>
      </FlexBox>
      <FlexBox flexDirection="row">
        <FlexBox.Item borderBottom>
          <FieldValue 
            description='Dia Padrao Vencimento'
            type='number'
            value={usuarioConfig.despesaDiaPadraoVencimento}
            editable={true}
            minValue={1}
            maxValue={28}
            onUpdate={handleDiaPadraoVencimento}
          />
        </FlexBox.Item>
      </FlexBox>
      <FlexBox flexDirection="row">
        <FlexBox.Item borderBottom>
          <FieldValue 
            description='Forma Pagamento Padrao'
            type='select'
            value={{ key: usuarioConfig.despesaFormaPagamentoPadrao?.id || 0, value: usuarioConfig.despesaFormaPagamentoPadrao?.descricao || ''}}
            editable={true}
            options={formasPagamento.map(formaPagamento => ({ key: formaPagamento.id, value: formaPagamento.descricao }))}
            onUpdate={handleFormaPagamento}
          />
        </FlexBox.Item>
      </FlexBox>
    </FlexBox>
  );
}

export default DespesaConfigForm;