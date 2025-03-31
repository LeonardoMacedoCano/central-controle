export type ResumoFluxoCaixa = {
  valorRendaUltimoMes: number;
  valorRendaAnoAtual: number;
  valorDespesaUltimoMes: number;
  valorDespesaAnoAtual: number;
  valorAtivosUltimoMes: number;
  valorAtivosAnoAtual: number;
  percentualMetasUltimoMes: number;
  percentualMetasAnoAtual: number;
  labelsMensalAnoAtual: string[];
  valoresRendaAnoAtual: number[];
  valoresDespesaAnoAtual: number[];
  valorRendaPassivaUltimoMes: number;
}

export const initialResumoFluxoCaixaState: ResumoFluxoCaixa = {
  valorRendaUltimoMes: 0,
  valorRendaAnoAtual: 0,
  valorDespesaUltimoMes: 0,
  valorDespesaAnoAtual: 0,
  valorAtivosUltimoMes: 0,
  valorAtivosAnoAtual: 0,
  percentualMetasUltimoMes: 0,
  percentualMetasAnoAtual: 0,
  labelsMensalAnoAtual: [],
  valoresRendaAnoAtual: [],
  valoresDespesaAnoAtual: [],
  valorRendaPassivaUltimoMes: 0
};