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
  if (tipo) {
    return TiposLancamentos[tipo].descricao;
  } else {
    return '';
  };
};

export const getCodigoTipoLancamento = (tipo?: TipoLancamentoEnum): string | undefined => {
  if (tipo) {
    return TiposLancamentos[tipo].codigo;
  } else {
    return undefined
  };
};

export const getTipoLancamentoByCodigo = (codigo: string): TipoLancamentoEnum | undefined => {
  const foundTipo = Object.values(TiposLancamentos).find(info => info.codigo === codigo);
  return foundTipo ? foundTipo.tipo : undefined;
};

export const tipoLancamentoOptions = [
  { key: getCodigoTipoLancamento('DESPESA'), value: getDescricaoTipoLancamento('DESPESA') },
  { key: getCodigoTipoLancamento('RECEITA'), value: getDescricaoTipoLancamento('RECEITA') },
  { key: getCodigoTipoLancamento('ATIVO'), value: getDescricaoTipoLancamento('ATIVO') },
];

export const tipoLancamentoFilters = [
  { key: 'DESPESA', value: getDescricaoTipoLancamento('DESPESA') },
  { key: 'RECEITA', value: getDescricaoTipoLancamento('RECEITA') },
  { key: 'ATIVO', value: getDescricaoTipoLancamento('ATIVO') },
];
