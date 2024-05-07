import React, { useContext, useEffect, useState } from 'react';
import { AuthContext } from '../../contexts/auth/AuthContext';
import { useMessage } from '../../contexts/message/ContextMessageProvider';
import UsuarioConfigService from '../../service/UsuarioConfigService';
import ParcelaService from '../../service/ParcelaService';
import { FormaPagamento } from '../../types/FormaPagamento';
import { UsuarioConfig } from '../../types/UsuarioConfig';
import Container from '../../components/container/Container';
import Panel from '../../components/panel/Panel';
import Tabs from '../../components/tabs/Tabs';
import DespesaConfigForm from '../../components/form/configuracao/DespesaConfigForm';
import FloatingButton from '../../components/button/floatingbutton/FloatingButton';
import { FaCheck } from 'react-icons/fa';

const initialUsuarioConfigState: UsuarioConfig = {
  id: 0,
  despesaNumeroItemPagina: 0,
  despesaValorMetaMensal: 0,
  despesaDiaPadraoVencimento: 0,
  despesaFormaPagamentoPadrao: null,
};

const ConfiguracaoPage: React.FC = () => {
  const [token, setToken] = useState<string | null>(null);
  const [usuarioConfig, setUsuarioConfig] = useState<UsuarioConfig>(initialUsuarioConfigState);
  const [formasPagamento, setFormasPagamento] = useState<FormaPagamento[]>([]);

  const auth = useContext(AuthContext);
  const message = useMessage();
  const usuarioConfigService = UsuarioConfigService();
  const parcelaService = ParcelaService();

  useEffect(() => {
    setToken(auth.usuario?.token || null);
  }, [auth.usuario?.token]);

  useEffect(() => {
    carregarConfiguracao();
    carregarFormasPagamento();
  }, [token]);

  const carregarConfiguracao = async () => {
    if (!token) return;

    try {
      const result = await usuarioConfigService.getUsuarioConfigByUsuario(token);
      if (result) setUsuarioConfig(result);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar as configurações do usuário.', error);
    }
  };

  const carregarFormasPagamento = async () => {
    if (!token) return;

    try {
      const result = await parcelaService.getTodasFormaPagamento(token);
      setFormasPagamento(result || []);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar as formas de pagamento.', error);
    }
  };

  const salvarUsuarioConfig = async () => {
    if (!token) return;

    try {
      await usuarioConfigService.editarUsuarioConfig(token, usuarioConfig.id, usuarioConfig);
    } catch (error) {
      message.showErrorWithLog('Erro ao salvar a Configuracao.', error);
    }

    carregarFormasPagamento();
  };

  const atualizarUsuarioConfig = (usuarioConfigAtualizado: UsuarioConfig) => {
    setUsuarioConfig(prevUsuarioConfig => ({ ...prevUsuarioConfig, ...usuarioConfigAtualizado }));
  };

  const tabs = [
    {
      label: 'Usuário',
      content: <div>Conteúdo da aba 1</div>,
    },
    {
      label: 'Despesa',
      content: <DespesaConfigForm usuarioConfig={usuarioConfig} formasPagamento={formasPagamento} onUpdate={atualizarUsuarioConfig} />,
    },
    {
      label: 'Teste 1',
      content: <div>Teste 1</div>,
    },
    {
      label: 'Teste 2',
      content: <div>Teste 2</div>,
    }
  ];

  return (
    <Container>
      <FloatingButton
        mainButtonIcon={<FaCheck />}
        mainButtonHint={'Salvar Configuração'}
        mainAction={salvarUsuarioConfig}
      />
      <Panel maxWidth='800px' title='Configuração'>
        <Tabs tabs={tabs} />
      </Panel>
    </Container>
  );
};

export default ConfiguracaoPage;
