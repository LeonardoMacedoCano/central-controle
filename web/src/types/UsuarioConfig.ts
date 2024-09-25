
export type UsuarioConfig = {
  id: number;
  despesaNumeroMaxItemPagina: number;
  despesaValorMetaMensal: number;
  despesaDiaPadraoVencimento: number;
}

export const initialUsuarioConfigState: UsuarioConfig = {
  id: 0,
  despesaNumeroMaxItemPagina: 0,
  despesaValorMetaMensal: 0,
  despesaDiaPadraoVencimento: 0,
};