export type TipoLancamentoEnum = 'DESPESA' | 'RECEITA' | 'PASSIVO' | 'ATIVO';

export interface TipoLancamentoInfo {
  tipo: TipoLancamentoEnum;
  descricao: string;
}

export const TipoLancamentoDescricoes: Record<TipoLancamentoEnum, TipoLancamentoInfo> = {
  DESPESA: { tipo: 'DESPESA', descricao: 'Despesa' },
  RECEITA: { tipo: 'RECEITA', descricao: 'Receita' },
  PASSIVO: { tipo: 'PASSIVO', descricao: 'Passivo' },
  ATIVO: { tipo: 'ATIVO', descricao: 'Ativo' },
};

export const getTipoLancamentoDescricao = (tipo: TipoLancamentoEnum): string => {
  return TipoLancamentoDescricoes[tipo].descricao;
};
