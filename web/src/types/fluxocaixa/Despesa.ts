import { Categoria, DespesaFormaPagamentoEnum } from "..";
import { getCurrentDate } from "../../utils";

export type Despesa = {
  id?: number;
  categoria?: Categoria;
  situacao: string;
  dataVencimento: Date;
  valor: number;
  pago: boolean;
  formaPagamento?: DespesaFormaPagamentoEnum;
}

export const initialDespesaState: Despesa = {
  situacao: "",
  dataVencimento: getCurrentDate(),
  valor: 0,
  pago: false
};
