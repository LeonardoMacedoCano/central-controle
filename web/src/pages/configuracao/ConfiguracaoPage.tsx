import React, { useContext, useEffect, useState } from 'react';
import { AuthContext } from '../../contexts/auth/AuthContext';
import { useMessage } from '../../contexts/message/ContextMessageProvider';
import UsuarioConfigService from '../../service/UsuarioConfigService';
import { UsuarioConfig, initialUsuarioConfigState } from '../../types/UsuarioConfig';
import Container from '../../components/container/Container';
import Panel from '../../components/panel/Panel';
import Tabs from '../../components/tabs/Tabs';
import DespesaConfigForm from '../../components/form/configuracao/DespesaConfigForm';
import FloatingButton from '../../components/button/floatingbutton/FloatingButton';
import { FaCheck } from 'react-icons/fa';

const ConfiguracaoPage: React.FC = () => {
  const [usuarioConfig, setUsuarioConfig] = useState<UsuarioConfig>(initialUsuarioConfigState);

  const auth = useContext(AuthContext);
  const message = useMessage();
  const usuarioConfigService = UsuarioConfigService();

  useEffect(() => {
    carregarConfiguracao();
  }, [auth.usuario?.token]);

  const carregarConfiguracao = async () => {
    if (!auth.usuario?.token) return;

    try {
      const result = await usuarioConfigService.getUsuarioConfigByUsuario(auth.usuario?.token);
      if (result) setUsuarioConfig(result);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar as configurações do usuário.', error);
    }
  };

  const salvarUsuarioConfig = async () => {
    if (!auth.usuario?.token) return;

    try {
      await usuarioConfigService.editarUsuarioConfig(auth.usuario?.token, usuarioConfig.id, usuarioConfig);
    } catch (error) {
      message.showErrorWithLog('Erro ao salvar a Configuracao.', error);
    }
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
      content: <DespesaConfigForm usuarioConfig={usuarioConfig} onUpdate={atualizarUsuarioConfig} />,
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
