import { Despesa } from "./fluxocaixa/Despesa";
import { TipoLancamentoEnum } from "./TipoLancamentoEnum";

export type LancamentoItemDTO = Despesa;

export type Lancamento = {
  id: number;
  dataLancamento: Date;
  descricao: string;
  tipo: TipoLancamentoEnum;
  itemDTO: LancamentoItemDTO;
};