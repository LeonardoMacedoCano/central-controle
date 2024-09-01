import { Categoria } from "../Categoria";
import { Parcela } from "./Parcela";

export type Despesa = {
  id: number;
  categoria: Categoria;
  valorTotal: number;
  situacao: string;
  parcelas: Parcela[];
}

export const initialDespesaState: Despesa = {
  id: 0,
  categoria: { id: 0, descricao: '' },
  valorTotal: 0,
  situacao: '',
  parcelas: []
};