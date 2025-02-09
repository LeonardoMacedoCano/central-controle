import { 
  Ativo,
  Despesa,
  Renda,
  TipoLancamentoEnum 
} from "./";

type LancamentoItemDTO = Despesa | Renda | Ativo;

export type Lancamento = {
  id: number;
  dataLancamento: Date;
  descricao: string;
  tipo?: TipoLancamentoEnum;
  itemDTO?: LancamentoItemDTO;
};