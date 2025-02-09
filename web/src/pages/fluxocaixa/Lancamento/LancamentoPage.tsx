import React, { useContext, useEffect, useState } from 'react';
import { Container, FieldValue, FlexBox, Loading, Panel } from '../../../components';
import { useParams } from 'react-router-dom';
import { Ativo, Despesa, getDescricaoTipoLancamento, Lancamento, Renda } from '../../../types';
import { AuthContext, useMessage } from '../../../contexts';
import { LancamentoService } from '../../../service';
import { formatDateToShortString } from '../../../utils';
import DespesaSection from './DespesaSection';
import RendaSection from './RendaSection';
import AtivoSection from './AtivoSection';

const LancamentoPage: React.FC = () => {
  const { id } = useParams<{ id?: string }>();
  const [lancamento, setLancamento] = useState<Lancamento>();
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const auth = useContext(AuthContext);
  const message = useMessage();
  const lancamentoService = LancamentoService();

  useEffect(() => {
    if (id) {
      setIsLoading(true);
      loadLancamento(id);
      setIsLoading(false);
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

  const renderLancamentoSection = () => {
    if (!lancamento || !lancamento.tipo || !lancamento.itemDTO) return null;
  
    switch (lancamento.tipo) {
      case 'DESPESA':
        return <DespesaSection despesa={lancamento.itemDTO as Despesa} />;
      case 'RENDA':
        return <RendaSection renda={lancamento.itemDTO as Renda} />;
      case 'ATIVO':
        return <AtivoSection ativo={lancamento.itemDTO as Ativo} />;
      default:
        return null;
    }
  };

  return (
    <Container>
      <Loading isLoading={isLoading} />
      {lancamento && (
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
                type='string'
                value={getDescricaoTipoLancamento(lancamento.tipo)}
              />
            </FlexBox.Item>
          </FlexBox>
          <FlexBox flexDirection="row">
            <FlexBox.Item borderTop>
              <FieldValue 
                description='Descrição'
                type='string'
                value={lancamento.descricao}
              />
            </FlexBox.Item>
          </FlexBox>
        </FlexBox>
      </Panel>
      )}
      {renderLancamentoSection()}
    </Container>
  );
};

export default LancamentoPage;
