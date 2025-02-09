import { Categoria } from "..";
import { getCurrentDate } from "../../utils";

export type Renda = {
  id?: number;
  categoria?: Categoria;
  valor: number;
  dataRecebimento: Date;
}

export const initialRendaState: Renda = {
  valor: 0,
  dataRecebimento: getCurrentDate(),
};
