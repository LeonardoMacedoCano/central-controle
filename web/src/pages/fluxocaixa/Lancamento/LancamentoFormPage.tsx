import React, { useContext, useEffect, useState } from 'react';
import { Container, FieldValue, FlexBox, FloatingButton, Panel } from '../../../components';
import { useNavigate, useParams } from 'react-router-dom';
import { getCodigoTipoLancamento, getDescricaoTipoLancamento, getTipoLancamentoByCodigo, Lancamento, tipoLancamentoOptions } from '../../../types';
import { AuthContext, useMessage } from '../../../contexts';
import { LancamentoService } from '../../../service';
import { formatDateToShortString, getCurrentDate } from '../../../utils';
import { FaCheck } from 'react-icons/fa';
import DespesaSectionForm from './DespesaSectionForm';
import { initialDespesaState } from '../../../types/fluxocaixa/Despesa';

const LancamentoFormPage: React.FC = () => {
  const { idStr } = useParams<{ idStr?: string }>();
  const [lancamento, setLancamento] = useState<Lancamento>({
    id: 0,
    dataLancamento: getCurrentDate(),
    descricao: ""
  });

  const auth = useContext(AuthContext);
  const message = useMessage();
  const lancamentoService = LancamentoService();
  const navigate = useNavigate();


  const id = typeof idStr === 'string' ? parseInt(idStr, 10) : 0;

  useEffect(() => {
    if (id > 0) {
      loadLancamento(id);
    }
  }, [auth.usuario?.token, id]);

  const loadLancamento = async (id: number) => {
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
    const selectedTipo = getTipoLancamentoByCodigo(Number(value)); 
    update({ tipo: selectedTipo });
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
    return (
      lancamento.descricao.trim() !== '' &&
      lancamento.tipo !== undefined &&
      (lancamento.tipo !== 'DESPESA' ||
        (lancamento.itemDTO?.categoria !== undefined &&
        lancamento.itemDTO?.formaPagamento !== undefined &&
        lancamento.itemDTO?.valor > 0)
      )
    );
  };

  const saveLancamento = async () => {
    if (!auth.usuario?.token) return;

    try {
      let responseId = 0;
      if (id > 0) {
        await lancamentoService.updateLancamento(auth.usuario?.token, id, lancamento);
        responseId = id;
      } else {
        const response = await lancamentoService.createLancamento(auth.usuario?.token, lancamento);
        if (response?.id) responseId = response.id;
      }
      if (responseId > 0) navigate(`/lancamento/${responseId}`);
    } catch (error) {
      message.showErrorWithLog('Erro ao salvar o lançamento.', error);
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

      {lancamento.tipo === 'DESPESA' && (
        <DespesaSectionForm 
          despesa={lancamento.itemDTO || initialDespesaState} 
          onUpdate={handleUpdateItem}  
        />
      )}
    </Container>
  );
};

export default LancamentoFormPage;
