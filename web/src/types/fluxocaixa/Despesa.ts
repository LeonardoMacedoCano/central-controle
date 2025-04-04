import { Categoria, DespesaFormaPagamentoEnum } from "..";
import { getCurrentDate } from "../../utils";

export type Despesa = {
  id?: number;
  categoria?: Categoria;
  dataVencimento: Date;
  valor: number;
  formaPagamento?: DespesaFormaPagamentoEnum;
}

export const initialDespesaState: Despesa = {
  dataVencimento: getCurrentDate(),
  valor: 0
};
