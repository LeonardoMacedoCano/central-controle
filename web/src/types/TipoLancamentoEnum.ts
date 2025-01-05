export type TipoLancamentoEnum = 'DESPESA' | 'RECEITA' | 'ATIVO';

export interface TipoLancamentoInfo {
  tipo: TipoLancamentoEnum;
  descricao: string;
  codigo: string;
}

export const TiposLancamentos: Record<TipoLancamentoEnum, TipoLancamentoInfo> = {
  DESPESA: { tipo: 'DESPESA', descricao: 'Despesa', codigo: '1' },
  RECEITA: { tipo: 'RECEITA', descricao: 'Receita', codigo: '2' },
  ATIVO: { tipo: 'ATIVO', descricao: 'Ativo', codigo: '3' },
};

export const getDescricaoTipoLancamento = (tipo?: TipoLancamentoEnum): string => {
  return tipo ? TiposLancamentos[tipo].descricao : '';
};

export const getCodigoTipoLancamento = (tipo?: TipoLancamentoEnum): string | undefined => {
  return tipo ? TiposLancamentos[tipo].codigo : undefined;
};

export const getTipoLancamentoByCodigo = (codigo: string): TipoLancamentoEnum | undefined => {
  return Object.values(TiposLancamentos).find(info => info.codigo === codigo)?.tipo;
};

const createTipoLancamentoOptions = (keys: TipoLancamentoEnum[]): { key: string; value: string }[] => {
  return keys.map(tipo => ({
    key: getCodigoTipoLancamento(tipo)!,
    value: getDescricaoTipoLancamento(tipo),
  }));
};

export const tipoLancamentoOptions = createTipoLancamentoOptions(['DESPESA', 'RECEITA', 'ATIVO']);

export const tipoLancamentoFilters = (['DESPESA', 'RECEITA', 'ATIVO'] as TipoLancamentoEnum[]).map(tipo => ({
  key: tipo,
  value: getDescricaoTipoLancamento(tipo),
}));


