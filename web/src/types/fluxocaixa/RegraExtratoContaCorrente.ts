import { Categoria } from "../Categoria";
import { AtivoCategoriaEnum } from "./AtivoCategoriaEnum";
import { TipoRegraExtratoContaCorrenteEnum } from "./TipoRegraExtratoContaCorrenteEnum";

export type RegraExtratoContaCorrente = {
  id: number;
  tipoRegra: TipoRegraExtratoContaCorrenteEnum;
  descricao: string;
  descricaoMatch: string;
  descricaoDestino?: string;
  despesaCategoriaDestino?: Categoria;
  rendaCategoriaDestino?: Categoria;
  ativoCategoriaDestino?: AtivoCategoriaEnum;
  prioridade: number;
  ativo: boolean;
}

export const initialRegraExtratoContaCorrenteState: RegraExtratoContaCorrente = {
  id: 0,
  tipoRegra: "CLASSIFICAR_DESPESA",
  descricao: "",
  descricaoMatch: "",
  prioridade: 0,
  ativo: true,
};