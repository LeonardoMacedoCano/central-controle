export type AtivoCategoriaEnum = 
  | 'ACAO_NACIONAL'
  | 'ACAO_INTERNACIONAL'
  | 'FUNDO_IMOBILIARIO'
  | 'ETF'
  | 'CRIPTOMOEDA'
  | 'DESCONHECIDO';

interface AtivoCategoriaInfo {
  tipo: AtivoCategoriaEnum;
  descricao: string;
  codigo: string;
}

const AtivoCategorias: Record<AtivoCategoriaEnum, AtivoCategoriaInfo> = {
  ACAO_NACIONAL: { tipo: 'ACAO_NACIONAL', descricao: 'Ação Nacional', codigo: '1' },
  ACAO_INTERNACIONAL: { tipo: 'ACAO_INTERNACIONAL', descricao: 'Ação Internacional', codigo: '2' },
  FUNDO_IMOBILIARIO: { tipo: 'FUNDO_IMOBILIARIO', descricao: 'Fundo Imobiliário', codigo: '3' },
  ETF: { tipo: 'ETF', descricao: 'ETF', codigo: '4' },
  CRIPTOMOEDA: { tipo: 'CRIPTOMOEDA', descricao: 'Criptomoeda', codigo: '5' },
  DESCONHECIDO: { tipo: 'DESCONHECIDO', descricao: 'Desconhecido', codigo: '6' },
};

export const getDescricaoAtivoCategoria = (tipo?: AtivoCategoriaEnum): string => {
  return tipo ? AtivoCategorias[tipo].descricao : '';
};

export const getCodigoAtivoCategoria = (tipo?: AtivoCategoriaEnum): string | undefined => {
  return tipo ? AtivoCategorias[tipo].codigo : undefined;
};

export const getAtivoCategoriaByCodigo = (codigo: string): AtivoCategoriaEnum | undefined => {
  const foundTipo = Object.values(AtivoCategorias).find(info => info.codigo === codigo);
  return foundTipo ? foundTipo.tipo : undefined;
};

export const ativoCategoriaOptions = Object.values(AtivoCategorias).map(info => ({
  key: info.codigo,
  value: info.descricao,
}));
