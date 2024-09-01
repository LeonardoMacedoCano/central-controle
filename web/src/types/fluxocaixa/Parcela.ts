import { FormaPagamento } from "./FormaPagamento";

export type Parcela = {
  id: number;
  numero: number;
  dataVencimento: Date;
  valor: number;
  pago: boolean;
  formaPagamento: FormaPagamento | null;
}