import { DockerStatusEnum } from "./DockerStatusEnum";

export type Servico = {
  id: number;
  nome: string;
  descricao: string;
	porta?: string;
	idarquivo?: number;
  status: DockerStatusEnum;
  permissao: boolean;
}