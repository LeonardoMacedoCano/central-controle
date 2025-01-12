import React, { useContext, useEffect, useState } from 'react';
import { Container, FieldValue, FlexBox, FloatingButton, Panel } from '../../../components';
import { useNavigate, useParams } from 'react-router-dom';
import { Ativo, getCodigoTipoLancamento, getDescricaoTipoLancamento, getTipoLancamentoByCodigo, initialAtivoState, initialReceitaState, Lancamento, Receita, tipoLancamentoOptions } from '../../../types';
import { AuthContext, useMessage } from '../../../contexts';
import { LancamentoService } from '../../../service';
import { formatDateToShortString, getCurrentDate, isDateValid } from '../../../utils';
import { FaCheck } from 'react-icons/fa';
import DespesaSectionForm from './DespesaSectionForm';
import { Despesa, initialDespesaState } from '../../../types/fluxocaixa/Despesa';
import ReceitaSectionForm from './ReceitaSectionForm';
import AtivoSectionForm from './AtivoSectionForm';

const LancamentoFormPage: React.FC = () => {
  const { id } = useParams<{ id?: string }>();
  const [lancamento, setLancamento] = useState<Lancamento>({
    id: 0,
    dataLancamento: getCurrentDate(),
    descricao: ""
  });

  const auth = useContext(AuthContext);
  const message = useMessage();
  const lancamentoService = LancamentoService();
  const navigate = useNavigate();

  useEffect(() => {
    if (id) {
      loadLancamento(id);
    }
  }, [auth.usuario?.token, id]);

  const loadLancamento = async (id: string) => {
    if (!auth.usuario?.token) return;

    try {
      const result = await lancamentoService.getLancamento(auth.usuario?.token, id);
      if (result) setLancamento(result);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar o lançamento.', error);
    }
  };

  const update = (updatedFields: Partial<Lancamento>) => {
    const updatedLancamento: Lancamento = {
      ...lancamento!,
      ...updatedFields,
    };
    setLancamento(prevLancamento => ({ ...prevLancamento, ...updatedLancamento }));
  };

  const handleUpdateTipo = (value: any) => {
    const selectedTipo = getTipoLancamentoByCodigo(String(value)); 
    update({ tipo: selectedTipo, itemDTO: undefined });
  };

  const handleUpdateDescricao = (value: any) => {
    if (typeof value === 'string') {
      update({ descricao: value });
    }
  };

  const handleUpdateItem = (value: any) => {
    update({ itemDTO: value });
  };

  const isRequiredFieldsFilled = (): boolean => {
    if (!lancamento.descricao.trim() || !lancamento.tipo || !lancamento.itemDTO) return false;
  
    const { tipo, itemDTO } = lancamento;
  
    if (tipo === 'DESPESA') {
      const despesa = itemDTO as Despesa;
      return (
        !!despesa.categoria && 
        !!despesa.formaPagamento && 
        despesa.valor > 0 &&
        isDateValid(despesa.dataVencimento)
      );
    }
  
    if (tipo === 'RECEITA') {
      const receita = itemDTO as Receita;
      return (
        !!receita.categoria && 
        receita.valor > 0
      );
    }
  
    if (tipo === 'ATIVO') {
      const ativo = itemDTO as Ativo;
      return (
        !!ativo.categoria &&
        !!ativo.ticker &&
        ativo.quantidade > 0 &&
        ativo.precoUnitario > 0 &&
        !!ativo.operacao &&
        isDateValid(ativo.dataMovimento)
      );      
    }
  
    return false;
  };

  const saveLancamento = async () => {
    if (!auth.usuario?.token) return;

    try {
      const response = await lancamentoService.saveLancamento(auth.usuario?.token, lancamento);
      if (response?.id) navigate(`/lancamento/${response.id}`);
    } catch (error) {
      message.showErrorWithLog('Erro ao salvar o lançamento.', error);
    }
  };

  const renderLancamentoSection = () => {
    if (!lancamento || !lancamento.tipo) return null;
  
    switch (lancamento.tipo) {
      case 'DESPESA':
        return <DespesaSectionForm despesa={lancamento.itemDTO as Despesa || initialDespesaState} onUpdate={handleUpdateItem} />;
      case 'RECEITA':
        return <ReceitaSectionForm receita={lancamento.itemDTO as Receita || initialReceitaState} onUpdate={handleUpdateItem} />;
      case 'ATIVO':
        return <AtivoSectionForm ativo={lancamento.itemDTO as Ativo || initialAtivoState} onUpdate={handleUpdateItem} />;
      default:
        return null;
    }
  };

  return (
    <Container>
      <FloatingButton
        mainButtonIcon={<FaCheck />}
        mainButtonHint={'Salvar'}
        mainAction={saveLancamento}
        disabled={!isRequiredFieldsFilled()}
      />
      <Panel maxWidth='1000px' title='Lançamento'>
        <FlexBox flexDirection="column">
          <FlexBox flexDirection="row">
            <FlexBox.Item borderRight>
              <FieldValue 
                description='Data Lançamento'
                type='string'
                value={formatDateToShortString(lancamento.dataLancamento)}
              />
            </FlexBox.Item>
            <FlexBox.Item>
              <FieldValue 
                description='Tipo'
                type='select'
                value={{ key: getCodigoTipoLancamento(lancamento.tipo), value: getDescricaoTipoLancamento(lancamento.tipo) }}
                editable={true}
                options={tipoLancamentoOptions}
                onUpdate={handleUpdateTipo}
              />
            </FlexBox.Item>
          </FlexBox>
          <FlexBox flexDirection="row">
            <FlexBox.Item borderTop>
              <FieldValue 
                description='Descrição'
                type='string'
                value={lancamento.descricao}
                editable={true}
                onUpdate={handleUpdateDescricao}
              />
            </FlexBox.Item>
          </FlexBox>
        </FlexBox>
      </Panel>

      {renderLancamentoSection()}
    </Container>
  );
};

export default LancamentoFormPage;
