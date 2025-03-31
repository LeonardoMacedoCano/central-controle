import React, { useContext, useEffect, useState } from 'react';
import { Container, CustomBarChart, CustomPieChart, InfoCard, Loading, Panel } from '../../../components';
import styled from 'styled-components';
import { getCurrentDate, VariantColor } from '../../../utils';
import { FluxoCaixaResumoService } from '../../../service';
import { initialResumoFluxoCaixaState, ResumoFluxoCaixa } from '../../../types';
import { AuthContext } from '../../../contexts';

type SummaryCardData = {
  title: string;
  descricao?: string;
  valueMensal: string;
  valueAnual: string;
  variant: VariantColor;
  onClick: () => void;
  mes: string;
  ano: number;
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

  const handleCardClick = {
    rendas: () => console.log("Rendas"),
    despesas: () => console.log("Despesas"),
    ativos: () => console.log("Ativos"),
    metas: () => console.log("Metas")
  };

  const currentMonth = resumo.labelsMensalAnoAtual[resumo.labelsMensalAnoAtual.length - 1];
  const currentYear = getCurrentDate().getFullYear();

  const summaryCardsData: SummaryCardData[] = [
    {
      title: 'Rendas',
      valueMensal: `R$ ${resumo.valorRendaMesAnterior}`,
      valueAnual: `R$ ${resumo.valorRendaAnoAtual}`,
      variant: 'success',
      onClick: handleCardClick.rendas,
      descricao: 'Total das rendas recebidas no mês e no ano até o momento.',
      mes: currentMonth,
      ano: currentYear
    },
    {
      title: 'Despesas',
      valueMensal: `R$ ${resumo.valorDespesaMesAnterior}`,
      valueAnual: `R$ ${resumo.valorDespesaAnoAtual}`,
      variant: 'warning',
      onClick: handleCardClick.despesas,
      descricao: 'Total das despesas realizadas no mês e acumuladas no ano.',
      mes: currentMonth,
      ano: currentYear
    },
    {
      title: 'Ativos',
      valueMensal: `R$ ${resumo.valorAtivosMesAnterior}`,
      valueAnual: `R$ ${resumo.valorAtivosAnoAtual}`,
      variant: 'info',
      onClick: handleCardClick.ativos,
      descricao: 'Valor total investido em ativos no mês e acumulado no ano.',
      mes: currentMonth,
      ano: currentYear
    },
    {
      title: 'Metas',
      valueMensal: `${resumo.percentualMetasMesAnterior}%`,
      valueAnual: `${resumo.percentualMetasAnoAtual}%`,
      variant: 'quaternary',
      onClick: handleCardClick.metas,
      descricao: 'Porcentagem de cumprimento das metas de rendimento para o mês e o ano.',
      mes: currentMonth,
      ano: currentYear
    },
  ];
  
  const renderSummaryCard = (cardData: SummaryCardData, index: number) => (
    <InfoCard 
      key={index} 
      height='170px'
      width='225px'
      variant={cardData.variant}
      title={cardData.title} 
      description={cardData.descricao}
      onClick={cardData.onClick}
    >
      <ValuesContainer>
        <p><span>Mês {cardData.mes}:</span> <span>{cardData.valueMensal}</span></p>
        <p><span>Ano {cardData.ano}:</span> <span>{cardData.valueAnual}</span></p>
      </ValuesContainer>
    </InfoCard>
  );

  return (
    <Container>
      <Loading isLoading={isLoading}/>

      <Panel maxWidth="1000px" title="Resumo Fluxo Caixa" transparent>
        {currentMonth && (
          <CardContainer>
            {summaryCardsData.map(renderSummaryCard)}
          </CardContainer>
        )}
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
    
      <Panel maxWidth="1000px">
        <CustomPieChart 
          title={`Renda Passiva x Despesa (Mês ${resumo.labelsMensalAnoAtual[resumo.labelsMensalAnoAtual.length - 1]})`} 
          data={[
            { name: "Renda Passiva", value: resumo.valorRendaPassivaMesAnterior, variant: 'success'},
            { name: "Despesa", value: resumo.valorDespesaMesAnterior, variant: 'warning' },
          ]} 
          showLegend 
          minVisualPercentage={3}
        />
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