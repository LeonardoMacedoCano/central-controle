import { Option } from "../Filters";

export type ServicoCategoria = {
  id: number;
  descricao: string;
	icone: string;
}

export function convertToOptions(servicoCategorias: ServicoCategoria[]): Option[] {
  return servicoCategorias.map((servico) => ({
    key: servico.descricao,
    value: servico.descricao,
  }));
}