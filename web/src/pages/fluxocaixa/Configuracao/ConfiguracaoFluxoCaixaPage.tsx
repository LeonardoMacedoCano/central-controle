import { Container, FloatingButton, Panel, Tabs } from "../../../components";
import { FaCheck } from 'react-icons/fa';
import DespesaConfigSectionForm from "./DespesaConfigSectionForm";
import { useContext, useEffect, useState } from "react";
import { FluxoCaixaConfig, initialFluxoCaixaConfigState } from "../../../types";
import { AuthContext, useMessage } from "../../../contexts";
import { FluxoCaixaConfigService } from "../../../service";
import ReceitaConfigSectionForm from "./ReceitaConfigSectionForm";

const ConfiguracaoFluxoCaixaPage: React.FC = () => {
  const [config, setConfig] = useState<FluxoCaixaConfig>(initialFluxoCaixaConfigState);

  const auth = useContext(AuthContext);
  const message = useMessage();
  const configService = FluxoCaixaConfigService();

  useEffect(() => {
    carregarConfiguracao();
  }, [auth.usuario?.token]);

  const carregarConfiguracao = async () => {
    if (!auth.usuario?.token) return;

    try {
      const result = await configService.getConfig(auth.usuario?.token);
      if (result) setConfig(result);
    } catch (error) {
      message.showErrorWithLog('Erro ao carregar as configurações do usuário.', error);
    }
  };

  const salvarConfig = async () => {
    if (!auth.usuario?.token) return;

    try {
      await configService.saveConfig(auth.usuario?.token, config);
    } catch (error) {
      message.showErrorWithLog('Erro ao salvar a configuracao.', error);
    }
  };

  const atualizarConfig = (configAtualizado: FluxoCaixaConfig) => {
    setConfig(prevConfig => ({ ...prevConfig, ...configAtualizado }));
  };

  const tabs = [
    {
      label: 'Despesa',
      content: <DespesaConfigSectionForm config={config} onUpdate={atualizarConfig} />,
    },
    {
      label: 'Receita',
      content: <ReceitaConfigSectionForm config={config} onUpdate={atualizarConfig} />,
    },
    {
      label: 'Ativo',
      content: <div>Conteúdo da aba 3</div>,
    },
    {
      label: 'Extrato',
      content: <div>Conteúdo da aba 4</div>,
    },
  ];

  return (
    <Container>
      <FloatingButton
        mainButtonIcon={<FaCheck />}
        mainButtonHint={'Salvar Configuração'}
        mainAction={salvarConfig}
      />
      <Panel maxWidth='800px' title='Configuração Fluxo Caixa'>
        <Tabs tabs={tabs} />
      </Panel>
    </Container>
  );
};

export default ConfiguracaoFluxoCaixaPage;