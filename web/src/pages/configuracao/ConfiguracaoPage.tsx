import React from 'react';
import Container from '../../components/container/Container';
import Panel from '../../components/panel/Panel';
import Tabs from '../../components/tabs/Tabs';

const ConfiguracaoPage: React.FC = () => {
  const tabs = [
    {
      label: 'Tab 1',
      content: <div>Conteúdo da aba 1</div>,
    },
    {
      label: 'Tab 2',
      content: <div>Conteúdo da aba 2</div>,
    },
    {
      label: 'Tab 3',
      content: <div>Conteúdo da aba 3</div>,
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
