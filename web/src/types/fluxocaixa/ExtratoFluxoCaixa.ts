export type ExtratoMensalCartaoDTO = {
  dataVencimento: Date;
  file: File | null;
}
export type ExtratoPadraoDTO = {
  file: File | null;
}

export type ExtratoFluxoCaixa = ExtratoMensalCartaoDTO | ExtratoPadraoDTO;