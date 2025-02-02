import { RouteObject } from 'react-router-dom';
import LancamentoListPage from '../pages/fluxocaixa/Lancamento/LancamentoListPage';
import LancamentoPage from '../pages/fluxocaixa/Lancamento/LancamentoPage';
import LancamentoFormPage from '../pages/fluxocaixa/Lancamento/LancamentoFormPage';
import ExtratoFluxoCaixaFormPage from '../pages/fluxocaixa/Extrato/ExtratoFluxoCaixaFormPage';
import FluxoCaixaParametrosPage from '../pages/fluxocaixa/Configuracao/Parametro/FluxoCaixaParametrosPage';
import FluxoCaixaConfigPage from '../pages/fluxocaixa/Configuracao/FluxoCaixaConfigPage';
import FluxoCaixaCategoriaListPage from '../pages/fluxocaixa/Configuracao/Categoria/FluxoCaixaCategoriaListPage';

const FluxoCaixaRoutes: RouteObject[] = [
  { path: "/lancamentos", element: <LancamentoListPage /> },
  { path: "/lancamento/novo", element: <LancamentoFormPage /> },
  { path: "/lancamento/editar/:id", element: <LancamentoFormPage /> },
  { path: "/lancamento/:id", element: <LancamentoPage /> },
  { path: "/extrato-fluxo-caixa", element: <ExtratoFluxoCaixaFormPage /> },
  { path: "/config-fluxo-caixa", element: <FluxoCaixaConfigPage /> },
  { path: "/parametros-fluxo-caixa", element: <FluxoCaixaParametrosPage /> },
  { path: "/categorias-fluxo-caixa", element: <FluxoCaixaCategoriaListPage /> },
];

export default FluxoCaixaRoutes;
