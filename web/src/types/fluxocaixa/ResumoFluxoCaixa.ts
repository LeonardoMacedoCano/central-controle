export type ResumoFluxoCaixa = {
  valorRendaMesAnterior: number;
  valorRendaAnoAtual: number;
  valorDespesaMesAnterior: number;
  valorDespesaAnoAtual: number;
  valorAtivosMesAnterior: number;
  valorAtivosAnoAtual: number;
  percentualMetasMesAnterior: number;
  percentualMetasAnoAtual: number;
  labelsMensalAnoAtual: string[];
  valoresRendaAnoAtual: number[];
  valoresDespesaAnoAtual: number[];
  valorRendaPassivaMesAnterior: number;
}

export const initialResumoFluxoCaixaState: ResumoFluxoCaixa = {
  valorRendaMesAnterior: 0,
  valorRendaAnoAtual: 0,
  valorDespesaMesAnterior: 0,
  valorDespesaAnoAtual: 0,
  valorAtivosMesAnterior: 0,
  valorAtivosAnoAtual: 0,
  percentualMetasMesAnterior: 0,
  percentualMetasAnoAtual: 0,
  labelsMensalAnoAtual: [],
  valoresRendaAnoAtual: [],
  valoresDespesaAnoAtual: [],
  valorRendaPassivaMesAnterior: 0
};