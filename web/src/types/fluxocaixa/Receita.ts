import { Categoria } from "..";

export type Receita = {
  id?: number;
  categoria?: Categoria;
  valor: number;
}

export const initialReceitaState: Receita = {
  valor: 0
};
