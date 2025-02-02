export type TipoRegraExtratoContaCorrenteEnum = | 'IGNORAR' | 'CLASSIFICAR';

interface TipoRegraExtratoContaCorrenteInfo {
  tipo: TipoRegraExtratoContaCorrenteEnum;
  descricao: string;
  codigo: string;
}

const TipoRegraExtratoContaCorrente: Record<TipoRegraExtratoContaCorrenteEnum, TipoRegraExtratoContaCorrenteInfo> = {
  IGNORAR: { tipo: 'IGNORAR', descricao: 'Ignorar', codigo: '1' },
  CLASSIFICAR: { tipo: 'CLASSIFICAR', descricao: 'Classificar', codigo: '2' },
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
