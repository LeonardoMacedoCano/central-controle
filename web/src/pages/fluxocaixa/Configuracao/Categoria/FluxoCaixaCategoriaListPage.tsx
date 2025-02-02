import React from 'react';
import { Container, Panel, Tabs } from '../../../../components';
import DespesaCategoriaSection from './DespesaCategoriaSection';
import ReceitaCategoriaSection from './ReceitaCategoriaSection';

const FluxoCaixaCategoriaListPage: React.FC = () => {
  const tabs = [
    {
      label: "Despesa",
      content: <DespesaCategoriaSection />,
    },
    {
      label: "Receita",
      content: <ReceitaCategoriaSection />,
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