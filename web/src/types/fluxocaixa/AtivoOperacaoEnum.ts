export type AtivoOperacaoEnum = 'COMPRA' | 'VENDA';

interface AtivoOperacaoInfo {
  tipo: AtivoOperacaoEnum;
  descricao: string;
  codigo: string;
}

const AtivoOperacoes: Record<AtivoOperacaoEnum, AtivoOperacaoInfo> = {
  COMPRA: { tipo: 'COMPRA', descricao: 'Compra', codigo: '1' },
  VENDA: { tipo: 'VENDA', descricao: 'Venda', codigo: '2' }
};

export const getDescricaoAtivoOperacao = (tipo?: AtivoOperacaoEnum): string => {
  return tipo ? AtivoOperacoes[tipo].descricao : '';
};

export const getCodigoAtivoOperacao = (tipo?: AtivoOperacaoEnum): string | undefined => {
  return tipo ? AtivoOperacoes[tipo].codigo : undefined;
};

export const getAtivoOperacaoByCodigo = (codigo: string): AtivoOperacaoEnum | undefined => {
  const foundTipo = Object.values(AtivoOperacoes).find(info => info.codigo === codigo);
  return foundTipo ? foundTipo.tipo : undefined;
};

export const ativoOperacaoOptions = Object.values(AtivoOperacoes).map(info => ({
  key: info.codigo,
  value: info.descricao,
}));
