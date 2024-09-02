import { 
  Despesa,
  TipoLancamentoEnum 
} from "./";

type LancamentoItemDTO = Despesa;

export type Lancamento = {
  id: number;
  dataLancamento: Date;
  descricao: string;
  tipo: TipoLancamentoEnum;
  itemDTO: LancamentoItemDTO;
};