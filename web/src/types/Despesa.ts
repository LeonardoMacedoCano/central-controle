import { getDataAtual } from "../utils/DateUtils";
import { Categoria } from "./Categoria";
import { Parcela } from "./Parcela";

export type Despesa = {
  id: number;
  categoria: Categoria;
  dataLancamento: Date;
  descricao: string;
  valorTotal: number;
  situacao: string;
  parcelas: Parcela[];
}

export const initialDespesaState: Despesa = {
  id: 0,
  categoria: { id: 0, descricao: '' },
  dataLancamento: getDataAtual(),
  descricao: '',
  valorTotal: 0,
  situacao: '',
  parcelas: []
};