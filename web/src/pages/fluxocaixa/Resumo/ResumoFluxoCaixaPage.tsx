import React, { useContext, useEffect, useState } from 'react';
import { Container, CustomBarChart, CustomPieChart, InfoCard, Loading, Panel } from '../../../components';
import styled from 'styled-components';
import { VariantColor } from '../../../utils';
import { FluxoCaixaResumoService } from '../../../service';
import { initialResumoFluxoCaixaState, ResumoFluxoCaixa } from '../../../types';
import { AuthContext } from '../../../contexts';

const handleRendasClick = () => {
  console.log("Rendas");
}

const handleDespesasClick = () => {
  console.log("Despesas");
}

const handleAtivosClick = () => {
  console.log("Ativos");
}

const handleMetasClick = () => {
  console.log("Metas");
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
  const [resumo, setResumo] = useState<ResumoFluxoCaixa>(initialResumoFluxoCaixaState);
  const [isLoading, setIsLoading] = useState<boolean>(false);

  const auth = useContext(AuthContext);
  const service = FluxoCaixaResumoService();

  useEffect(() => {
    if (!auth.usuario?.token) return;
  
    loadResumo()
  }, [auth.usuario?.token]);
  
  const loadResumo = async () => {
    setIsLoading(true);
    try {
      const result = await service.getResumoFluxoCaixa(auth.usuario!.token);
      if (result) setResumo(result);
    } finally {
      setIsLoading(false);
    }
  };

  const summaryData: SummaryData[] = [
    {
      title: 'Rendas',
      valueMensal: `R$ ${resumo.valorRendaMesAtual}`,
      valueAnual: `R$ ${resumo.valorRendaAnoAtual}`,
      variant: 'success',
      onClick: handleRendasClick,
      descricao: 'Total das rendas recebidas no mês e no ano até o momento.'
    },
    {
      title: 'Despesas',
      valueMensal: `R$ ${resumo.valorDespesaMesAtual}`,
      valueAnual: `R$ ${resumo.valorDespesaAnoAtual}`,
      variant: 'warning',
      onClick: handleDespesasClick,
      descricao: 'Total das despesas realizadas no mês e acumuladas no ano.'
    },
    {
      title: 'Ativos',
      valueMensal: `R$ ${resumo.valorAtivosMesAtual}`,
      valueAnual: `R$ ${resumo.valorAtivosAnoAtual}`,
      variant: 'info',
      onClick: handleAtivosClick,
      descricao: 'Valor total investido em ativos no mês e acumulado no ano.'
    },
    {
      title: 'Metas',
      valueMensal: `${resumo.percentualMetasMesAtual}%`,
      valueAnual: `${resumo.percentualMetasAnoAtual}%`,
      variant: 'quaternary',
      onClick: handleMetasClick,
      descricao: 'Porcentagem de cumprimento das metas de rendimento para o mês e o ano.'
    },
  ];  

  return (
    <Container>
      <Loading isLoading={isLoading}/>
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
      <Panel maxWidth="1000px" >
        <CustomBarChart 
          title='Renda x Despesa'
          showGridLines={true}
          data={{
            labels: resumo.labelsMensalAnoAtual,
            series: [
              {
                name: "Renda",
                variant: "success",
                data: resumo.valoresRendaAnoAtual
              },
              {
                name: "Despesa",
                variant: "warning",
                data: resumo.valoresDespesaAnoAtual
              }
            ]
          }}
        />
      </Panel>
      <Panel maxWidth="1000px" >
        <CustomPieChart title="Renda Passiva x Despesa (Mês atual)" data={
          [
            { name: "Renda Passiva", value: resumo.valorRendaPassivaMesAtual, variant: 'success'},
            { name: "Despesa", value: resumo.valorDespesaMesAtual, variant: 'warning' },
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