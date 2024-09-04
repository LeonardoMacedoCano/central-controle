export type TipoLancamentoEnum = 'DESPESA' | 'RECEITA' | 'PASSIVO' | 'ATIVO';

export interface TipoLancamentoInfo {
  tipo: TipoLancamentoEnum;
  descricao: string;
  codigo: number;
}

export const TipoLancamentoDescricoes: Record<TipoLancamentoEnum, TipoLancamentoInfo> = {
  DESPESA: { tipo: 'DESPESA', descricao: 'Despesa', codigo: 1 },
  RECEITA: { tipo: 'RECEITA', descricao: 'Receita', codigo: 2 },
  PASSIVO: { tipo: 'PASSIVO', descricao: 'Passivo', codigo: 3 },
  ATIVO: { tipo: 'ATIVO', descricao: 'Ativo', codigo: 4 },
};

export const getTipoLancamentoDescricao = (tipo?: TipoLancamentoEnum): string => {
  if (tipo) {
    return TipoLancamentoDescricoes[tipo].descricao;
  } else {
    return '';
  };
};

export const getTipoLancamentoCodigo = (tipo?: TipoLancamentoEnum): number | undefined => {
  if (tipo) {
    return TipoLancamentoDescricoes[tipo].codigo;
  } else {
    return undefined
  };
};

export const getTipoLancamentoByCodigo = (codigo: number): TipoLancamentoEnum | undefined => {
  const foundTipo = Object.values(TipoLancamentoDescricoes).find(info => info.codigo === codigo);
  return foundTipo ? foundTipo.tipo : undefined;
};

export const tipoLancamentoOptions = [
  { key: getTipoLancamentoCodigo('DESPESA'), value: getTipoLancamentoDescricao('DESPESA') },
  { key: getTipoLancamentoCodigo('RECEITA'), value: getTipoLancamentoDescricao('RECEITA') },
  { key: getTipoLancamentoCodigo('PASSIVO'), value: getTipoLancamentoDescricao('PASSIVO') },
  { key: getTipoLancamentoCodigo('ATIVO'), value: getTipoLancamentoDescricao('ATIVO') },
];
