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
  if (tipo) {
    return AtivoOperacoes[tipo].descricao;
  } else {
    return '';
  };
};

export const getCodigoAtivoOperacao = (tipo?: AtivoOperacaoEnum): string | undefined => {
  if (tipo) {
    return AtivoOperacoes[tipo].codigo;
  } else {
    return undefined
  };
};

export const getAtivoOperacaoByCodigo = (codigo: string): AtivoOperacaoEnum | undefined => {
  const foundTipo = Object.values(AtivoOperacoes).find(info => info.codigo === codigo);
  return foundTipo ? foundTipo.tipo : undefined;
};

export const ativoOperacaoOptions = [
  { key: getCodigoAtivoOperacao('COMPRA'), value: getDescricaoAtivoOperacao('COMPRA') },
  { key: getCodigoAtivoOperacao('VENDA'), value: getDescricaoAtivoOperacao('VENDA') },
];

