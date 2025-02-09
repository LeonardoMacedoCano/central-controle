import { Categoria } from "../Categoria";

export type FluxoCaixaParametro = {
  id?: number;
  despesaCategoriaPadrao?: Categoria;
  metaLimiteDespesaMensal?: number;
  rendaCategoriaPadrao?: Categoria;
  rendaPassivaCategoria?: Categoria;
  metaAporteMensal?: number;
  metaAporteTotal?: number;
  diaPadraoVencimentoCartao: number;
}

export const initialFluxoCaixaParametroState: FluxoCaixaParametro = {
  diaPadraoVencimentoCartao: 10,
};