import { Categoria } from "../Categoria";

export type FluxoCaixaConfig = {
  id?: number;
  despesaCategoriaPadrao?: Categoria;
  metaLimiteDespesaMensal?: number;
  receitaCategoriaPadrao?: Categoria;
  receitaCategoriaParaGanhoAtivo?: Categoria;
  metaAporteMensal?: number;
  metaAporteTotal?: number;
  diaPadraoVencimentoFatura?: number;
}

export const initialFluxoCaixaConfigState: FluxoCaixaConfig = {
  diaPadraoVencimentoFatura: 10
};