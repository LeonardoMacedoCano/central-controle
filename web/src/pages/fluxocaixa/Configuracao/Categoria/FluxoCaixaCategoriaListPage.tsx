import React from 'react';
import { Container, Panel, Tabs } from '../../../../components';
import DespesaCategoriaSection from './DespesaCategoriaSection';
import RendaCategoriaSection from './RendaCategoriaSection';

const FluxoCaixaCategoriaListPage: React.FC = () => {
  const tabs = [
    {
      label: "Despesa",
      content: <DespesaCategoriaSection />,
    },
    {
      label: "Renda",
      content: <RendaCategoriaSection />,
    },
  ];

  return (
    <Container>
      <Panel maxWidth="1000px" title="Categorias Fluxo Caixa">
        <Tabs tabs={tabs} />
      </Panel>
    </Container>
  );
};

export default FluxoCaixaCategoriaListPage;