import { AtivoCategoriaEnum, AtivoOperacaoEnum } from "..";
import { getCurrentDate } from "../../utils";

export type Ativo = {
  id?: number;
  categoria?: AtivoCategoriaEnum;
  operacao?: AtivoOperacaoEnum;
  valor: number;
  dataMovimento: Date;
}

export const initialAtivoState: Ativo = {
  dataMovimento: getCurrentDate(),
  valor: 0
};
