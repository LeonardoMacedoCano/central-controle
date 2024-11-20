import { AtivoOperacaoEnum, Categoria } from "..";
import { getCurrentDate } from "../../utils";

export type Ativo = {
  id?: number;
  categoria?: Categoria;
  ticker: string;
  operacao?: AtivoOperacaoEnum;
  quantidade: number;
  precoUnitario: number;
  dataMovimento: Date;
}

export const initialAtivoState: Ativo = {
  dataMovimento: getCurrentDate(),
  quantidade: 0,
  precoUnitario: 0,
  ticker: ''
};
