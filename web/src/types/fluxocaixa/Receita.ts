import { Categoria } from "..";
import { getCurrentDate } from "../../utils";

export type Receita = {
  id?: number;
  categoria?: Categoria;
  valor: number;
  dataRecebimento: Date;
}

export const initialReceitaState: Receita = {
  valor: 0,
  dataRecebimento: getCurrentDate(),
};
