import React from 'react';
import { Container, CustomBarChart, CustomPieChart, InfoCard, Panel } from '../../../components';
import styled from 'styled-components';
import { VariantColor } from '../../../utils';

const handleClick = () => {
  console.log("Opa");
}

type SummaryData = {
  title: string;
  valueMensal: string;
  valueAnual: string;
  variant: VariantColor;
  onClick: () => void;
};

const ResumoFluxoCaixaPage: React.FC = () => {
  const summaryData: SummaryData[] = [
    {
      title: 'Rendas', 
      valueMensal: 'R$ 4.000', 
      valueAnual: 'R$ 50.000', 
      variant: 'success',
      onClick: handleClick
    },
    {
      title: 'Despesas', 
      valueMensal: 'R$ 2.000', 
      valueAnual: 'R$ 25.000', 
      variant: 'warning',
      onClick: handleClick
    },
    {
      title: 'Ativos', 
      valueMensal: 'R$ 1.800', 
      valueAnual: 'R$ 20.000', 
      variant: 'info',
      onClick: handleClick
    },
    {
      title: 'Metas', 
      valueMensal: '100%', 
      valueAnual: '80%', 
      variant: 'info',
      onClick: handleClick
    },
  ];

  return (
    <Container>
      <Panel 
        maxWidth="1000px" 
        title="Resumo Fluxo Caixa"
        transparent  
      >
        <CardContainer>
          {summaryData.map((item, index) => (
            <InfoCard 
              key={index} 
              variant={item.variant}
              title={item.title} 
              description="teste"
              onClick={item.onClick}
            >
              <p>Mensal: {item.valueMensal}</p>
              <p>Anual: {item.valueAnual}</p>
            </InfoCard>
          ))}
        </CardContainer>
      </Panel>
      <Panel maxWidth="1000px">
      <CustomBarChart 
        title='Renda x Despesa'
        showGridLines={true}
        data={{
          labels: ["Jan/25", "Fev/25", "Mar/25", "Abr/25", "Mai/25", "Jun/25", "Jul/25", "Ago/25", "Set/25", "Out/25", "Nov/25", "Dez/25"],
          series: [
            {
              name: "Renda",
              variant: "success",
              data: [4000, 4200, 4000, 4000, 4200, 4000, 4000, 4200, 4000, 4000, 4200, 4000]
            },
            {
              name: "Despesa",
              variant: "warning",
              data: [1800, 2000, 1900, 1800, 2000, 1900, 1800, 2000, 1900, 1800, 2000, 1900]
            }
          ]
        }}
      />
      </Panel>
      <Panel maxWidth="1000px" title="Teste">
        <CustomPieChart title="Despesas Mensais" data={
          [
            { name: "Alimentação", value: 500},
            { name: "Transporte", value: 300 },
            { name: "Lazer", value: 200},
            { name: "Saúde", value: 100},
            { name: "a", value: 500},
            { name: "b", value: 300 },
            { name: "c", value: 200},
            { name: "d", value: 100},
          ]
        } showLegend />
      </Panel>
    </Container>
  );
};

export default ResumoFluxoCaixaPage;

const CardContainer = styled.div`
  width: 100%;
  display: flex; 
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
  margin: 10px;
  gap: 20px;
`;