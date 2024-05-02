import React from 'react';
import Container from '../../components/container/Container';
import Panel from '../../components/panel/Panel';
import Tabs from '../../components/tabs/Tabs';

const ConfiguracaoPage: React.FC = () => {
  const tabs = [
    {
      label: 'Usuário',
      content: <div>Conteúdo da aba 1</div>,
    },
    {
      label: 'Despesa',
      content: <div>Conteúdo da aba 2</div>,
    },
  ];

  return (
    <Container>
      <Panel maxWidth='1000px' title='Configuração'>
        <Tabs tabs={tabs} />
      </Panel>
    </Container>
  );
};

export default ConfiguracaoPage;
