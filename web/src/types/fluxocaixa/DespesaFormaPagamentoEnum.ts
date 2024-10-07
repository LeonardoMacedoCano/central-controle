export type DespesaFormaPagamentoEnum = 'DINHEIRO' | 'PIX' | 'CARTAO_CREDITO' | 'CARTAO_DEBITO';

export interface DespesaFormaPagamentoInfo {
  tipo: DespesaFormaPagamentoEnum;
  descricao: string;
  codigo: string;
}

export const DespesaFormasPagamentos: Record<DespesaFormaPagamentoEnum, DespesaFormaPagamentoInfo> = {
  DINHEIRO: { tipo: 'DINHEIRO', descricao: 'Dinheiro', codigo: '1' },
  PIX: { tipo: 'PIX', descricao: 'PIX', codigo: '2' },
  CARTAO_CREDITO: { tipo: 'CARTAO_CREDITO', descricao: 'Cartão de Crédito', codigo: '3' },
  CARTAO_DEBITO: { tipo: 'CARTAO_DEBITO', descricao: 'Cartão de Débito', codigo: '4' },
};

export const getDescricaoDespesaFormaPagamento = (tipo?: DespesaFormaPagamentoEnum): string => {
  if (tipo) {
    return DespesaFormasPagamentos[tipo].descricao;
  } else {
    return '';
  };
};

export const getCodigoDespesaFormaPagamento = (tipo?: DespesaFormaPagamentoEnum): string | undefined => {
  if (tipo) {
    return DespesaFormasPagamentos[tipo].codigo;
  } else {
    return undefined
  };
};

export const getDespesaFormaPagamentoByCodigo = (codigo: string): DespesaFormaPagamentoEnum | undefined => {
  const foundTipo = Object.values(DespesaFormasPagamentos).find(info => info.codigo === codigo);
  return foundTipo ? foundTipo.tipo : undefined;
};

export const despesaFormaPagamentoOptions = [
  { key: getCodigoDespesaFormaPagamento('DINHEIRO'), value: getDescricaoDespesaFormaPagamento('DINHEIRO') },
  { key: getCodigoDespesaFormaPagamento('PIX'), value: getDescricaoDespesaFormaPagamento('PIX') },
  { key: getCodigoDespesaFormaPagamento('CARTAO_CREDITO'), value: getDescricaoDespesaFormaPagamento('CARTAO_CREDITO') },
  { key: getCodigoDespesaFormaPagamento('CARTAO_DEBITO'), value: getDescricaoDespesaFormaPagamento('CARTAO_DEBITO') },
];

