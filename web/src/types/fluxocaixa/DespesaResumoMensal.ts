import { Categoria } from "../";

export type DespesaResumoMensal = {
  id: number;
  categoria: Categoria;
  descricao: string;
  valorTotal: number;
  situacao: string;
}