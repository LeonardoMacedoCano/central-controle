import { 
  Ativo,
  Despesa,
  Receita,
  TipoLancamentoEnum 
} from "./";

type LancamentoItemDTO = Despesa | Receita | Ativo;

export type Lancamento = {
  id: number;
  dataLancamento: Date;
  descricao: string;
  tipo?: TipoLancamentoEnum;
  itemDTO?: LancamentoItemDTO;
};