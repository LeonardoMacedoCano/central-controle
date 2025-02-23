export type ResumoFluxoCaixa = {
  valorRendaMesAtual: number;
  valorRendaAnoAtual: number;
  valorDespesaMesAtual: number;
  valorDespesaAnoAtual: number;
  valorAtivosMesAtual: number;
  valorAtivosAnoAtual: number;
  percentualMetasMesAtual: number;
  percentualMetasAnoAtual: number;
  labelsMensalAnoAtual: string[];
  valoresRendaAnoAtual: number[];
  valoresDespesaAnoAtual: number[];
  valorRendaPassivaMesAtual: number;
}

export const initialResumoFluxoCaixaState: ResumoFluxoCaixa = {
  valorRendaMesAtual: 0,
  valorRendaAnoAtual: 0,
  valorDespesaMesAtual: 0,
  valorDespesaAnoAtual: 0,
  valorAtivosMesAtual: 0,
  valorAtivosAnoAtual: 0,
  percentualMetasMesAtual: 0,
  percentualMetasAnoAtual: 0,
  labelsMensalAnoAtual: [],
  valoresRendaAnoAtual: [],
  valoresDespesaAnoAtual: [],
  valorRendaPassivaMesAtual: 0
};