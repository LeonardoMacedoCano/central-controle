import React from 'react';
import { Container, CustomBarChart, CustomPieChart, InfoCard, Panel } from '../../../components';
import styled from 'styled-components';
import { VariantColor } from '../../../utils';

const handleClick = () => {
  console.log("Opa");
}

type SummaryData = {
  title: string;
  descricao?: string;
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
      onClick: handleClick,
      descricao: 'Total das rendas recebidas no mês e no ano até o momento.'
    },
    {
      title: 'Despesas',
      valueMensal: 'R$ 2.000',
      valueAnual: 'R$ 25.000',
      variant: 'warning',
      onClick: handleClick,
      descricao: 'Total das despesas realizadas no mês e acumuladas no ano.'
    },
    {
      title: 'Ativos',
      valueMensal: 'R$ 1.800',
      valueAnual: 'R$ 20.000',
      variant: 'info',
      onClick: handleClick,
      descricao: 'Valor total investido em ativos no mês e acumulado no ano.'
    },
    {
      title: 'Metas',
      valueMensal: '100%',
      valueAnual: '80%',
      variant: 'quaternary',
      onClick: handleClick,
      descricao: 'Porcentagem de cumprimento das metas de rendimento para o mês e o ano.'
    },
  ];  

  return (
    <Container>
      <Panel maxWidth="1000px" title="Resumo Fluxo Caixa" transparent>
        <CardContainer>
          {summaryData.map((item, index) => (
            <InfoCard 
              key={index} 
              height='170px'
              width='225px'
              variant={item.variant}
              title={item.title} 
              description={item.descricao}
              onClick={item.onClick}
            >
              <ValuesContainer>
                <p><span>Mensal:</span> <span>{item.valueMensal}</span></p>
                <p><span>Anual:</span> <span>{item.valueAnual}</span></p>
              </ValuesContainer>
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
      <Panel maxWidth="1000px">
        <CustomPieChart title="Renda Passiva x Despesa (Mês atual)" data={
          [
            { name: "Renda Passiva", value: 100, variant: 'success'},
            { name: "Despesa", value: 4000, variant: 'warning' }
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
  gap: 25px;
`;

const ValuesContainer = styled.div`
  text-align: justify;
  display: flex;
  flex-direction: column;
  gap: 4px;
  font-size: 14px;

  p {
    display: flex;
    justify-content: space-between;
    width: 100%;
  }

  span:first-child {
    font-weight: bold;
  }
`;