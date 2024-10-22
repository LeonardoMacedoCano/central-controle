import { DockerStatusEnum } from "./DockerStatusEnum";
import { ServicoCategoria } from "./ServicoCategoria";

export type Servico = {
  id: number;
  nome: string;
  descricao: string;
	porta?: string;
	idarquivo?: number;
  status: DockerStatusEnum;
  permissao: boolean;
  categorias: ServicoCategoria[];
}