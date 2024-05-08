import { FormaPagamento } from "./FormaPagamento";

export type UsuarioConfig = {
  id: number;
  despesaNumeroItemPagina: number;
  despesaValorMetaMensal: number;
  despesaDiaPadraoVencimento: number;
  despesaFormaPagamentoPadrao: FormaPagamento | null;
}

export const initialUsuarioConfigState: UsuarioConfig = {
  id: 0,
  despesaNumeroItemPagina: 0,
  despesaValorMetaMensal: 0,
  despesaDiaPadraoVencimento: 0,
  despesaFormaPagamentoPadrao: null,
};