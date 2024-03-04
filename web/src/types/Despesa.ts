import { Categoria } from "./Categoria";
import { Parcela } from "./Parcela";

export type Despesa = {
  id: number;
  categoria: Categoria;
  dataLancamento: Date;
  descricao: string;
  parcelas: Parcela[];
}