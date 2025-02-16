import React from 'react';
import { Container, CustomBarChart, InfoCard, Panel } from '../../../components';
import styled from 'styled-components';
import { VariantColor } from '../../../utils';
import { BarChartData } from '../../../components/chart/CustomBarChart';

type PieChartData = {
  name: string;
  value: number;
};

type CustomPieChartProps = {
  data: PieChartData[];
  colors: string[];
};

const handleClick = () => {
  console.log("Opa");
}

const CustomPieChart: React.FC<CustomPieChartProps> = ({ data, colors }) => {
  const total = data.reduce((sum, entry) => sum + entry.value, 0);
  let cumulative = 0;

  return (
    <svg width="200" height="200" viewBox="0 0 32 32">
      {data.map((entry, index) => {
        const [startX, startY] = [Math.cos(2 * Math.PI * cumulative / total) * 16, Math.sin(2 * Math.PI * cumulative / total) * 16];
        cumulative += entry.value;
        const [endX, endY] = [Math.cos(2 * Math.PI * cumulative / total) * 16, Math.sin(2 * Math.PI * cumulative / total) * 16];
        const largeArc = entry.value / total > 0.5 ? 1 : 0;
        return (
          <path
            key={index}
            d={`M16,16 L${16 + startX},${16 + startY} A16,16 0 ${largeArc},1 ${16 + endX},${16 + endY} Z`}
            fill={colors[index]}
          />
        );
      })}
    </svg>
  );
};

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

  const pieData: PieChartData[] = [
    { name: 'Renda Passiva', value: 30 },
    { name: 'Outras Rendas', value: 70 },
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
          labels: ["Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"],
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
        <div className="grid grid-cols-2 gap-4 mt-4">
          <CustomPieChart data={pieData} colors={['#ef4444', '#84cc16']} />
        </div>
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