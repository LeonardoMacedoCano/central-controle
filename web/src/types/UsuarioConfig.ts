import { FormaPagamento } from "./FormaPagamento";

export type UsuarioConfig = {
  id: number;
  despesaNumeroItemPagina: number;
  despesaValorMetaMensal: number;
  despesaDiaPadraoVencimento: number;
  despesaFormaPagamentoPadrao: FormaPagamento | null;
}