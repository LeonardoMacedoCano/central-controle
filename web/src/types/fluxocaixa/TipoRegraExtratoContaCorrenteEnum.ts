export type TipoRegraExtratoContaCorrenteEnum = | 'IGNORAR_DESPESA' | 'CLASSIFICAR_DESPESA' | 'IGNORAR_RECEITA' | 'CLASSIFICAR_RECEITA';

interface TipoRegraExtratoContaCorrenteInfo {
  tipo: TipoRegraExtratoContaCorrenteEnum;
  descricao: string;
  codigo: string;
}

const TipoRegraExtratoContaCorrente: Record<TipoRegraExtratoContaCorrenteEnum, TipoRegraExtratoContaCorrenteInfo> = {
  IGNORAR_DESPESA: { tipo: 'IGNORAR_DESPESA', descricao: 'Ignorar Despesa', codigo: '1' },
  CLASSIFICAR_DESPESA: { tipo: 'CLASSIFICAR_DESPESA', descricao: 'Classificar Despesa', codigo: '2' },
  IGNORAR_RECEITA: { tipo: 'IGNORAR_RECEITA', descricao: 'Ignorar Receita', codigo: '3' },
  CLASSIFICAR_RECEITA: { tipo: 'CLASSIFICAR_RECEITA', descricao: 'Classificar Receita', codigo: '4' },
};

export const getDescricaoTipoRegraExtratoContaCorrente = (tipo?: TipoRegraExtratoContaCorrenteEnum): string => {
  return tipo ? TipoRegraExtratoContaCorrente[tipo].descricao : '';
};

export const getCodigoTipoRegraExtratoContaCorrente = (tipo?: TipoRegraExtratoContaCorrenteEnum): string | undefined => {
  return tipo ? TipoRegraExtratoContaCorrente[tipo].codigo : undefined;
};

export const getTipoRegraExtratoContaCorrenteByCodigo = (codigo: string): TipoRegraExtratoContaCorrenteEnum | undefined => {
  const foundTipo = Object.values(TipoRegraExtratoContaCorrente).find(info => info.codigo === codigo);
  return foundTipo ? foundTipo.tipo : undefined;
};

export const tipoRegraExtratoContaCorrenteOptions = Object.values(TipoRegraExtratoContaCorrente).map(info => ({
  key: info.codigo,
  value: info.descricao,
}));
