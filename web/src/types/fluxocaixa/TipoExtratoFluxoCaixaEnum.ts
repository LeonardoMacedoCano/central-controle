export type TipoExtratoFluxoCaixaEnum = 
  | 'EXTRATO_MENSAL_CARTAO'
  | 'EXTRATO_CONTA_CORRENTE'
  | 'EXTRATO_ATIVOS_B3';

interface TipoExtratoFluxoCaixaInfo {
  tipo: TipoExtratoFluxoCaixaEnum;
  descricao: string;
  codigo: string;
}

const TipoExtratoFluxoCaixa: Record<TipoExtratoFluxoCaixaEnum, TipoExtratoFluxoCaixaInfo> = {
  EXTRATO_MENSAL_CARTAO: { tipo: 'EXTRATO_MENSAL_CARTAO', descricao: 'Extrato mensal do cartão', codigo: '1' },
  EXTRATO_CONTA_CORRENTE: { tipo: 'EXTRATO_CONTA_CORRENTE', descricao: 'Extrato da conta corrente', codigo: '2' },
  EXTRATO_ATIVOS_B3: { tipo: 'EXTRATO_ATIVOS_B3', descricao: 'Extrato dos ativos na B3', codigo: '3' },
};

export const getDescricaoTipoExtratoFluxoCaixa = (tipo?: TipoExtratoFluxoCaixaEnum): string => {
  return tipo ? TipoExtratoFluxoCaixa[tipo].descricao : '';
};

export const getCodigoTipoExtratoFluxoCaixa = (tipo?: TipoExtratoFluxoCaixaEnum): string | undefined => {
  return tipo ? TipoExtratoFluxoCaixa[tipo].codigo : undefined;
};

export const getTipoExtratoFluxoCaixaByCodigo = (codigo: string): TipoExtratoFluxoCaixaEnum | undefined => {
  const foundTipo = Object.values(TipoExtratoFluxoCaixa).find(info => info.codigo === codigo);
  return foundTipo ? foundTipo.tipo : undefined;
};

export const tipoExtratoFluxoCaixaOptions = Object.values(TipoExtratoFluxoCaixa).map(info => ({
  key: info.codigo,
  value: info.descricao,
}));
