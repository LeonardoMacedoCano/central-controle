import { RouteObject } from 'react-router-dom';
import LancamentoListPage from '../pages/fluxocaixa/Lancamento/LancamentoListPage';
import LancamentoPage from '../pages/fluxocaixa/Lancamento/LancamentoPage';
import LancamentoFormPage from '../pages/fluxocaixa/Lancamento/LancamentoFormPage';
import ExtratoFaturaCartaoFormPage from '../pages/fluxocaixa/Lancamento/ExtratoFaturaCartaoFormPage';
import ConfiguracaoFluxoCaixaPage from '../pages/fluxocaixa/Configuracao/ConfiguracaoFluxoCaixaPage';

const FluxoCaixaRoutes: RouteObject[] = [
  { path: "/lancamentos", element: <LancamentoListPage /> },
  { path: "/lancamento/novo", element: <LancamentoFormPage /> },
  { path: "/lancamento/editar/:id", element: <LancamentoFormPage /> },
  { path: "/lancamento/:id", element: <LancamentoPage /> },
  { path: "/extrato-fatura-cartao", element: <ExtratoFaturaCartaoFormPage /> },
  { path: "/config-fluxo-caixa", element: <ConfiguracaoFluxoCaixaPage /> },
];

export default FluxoCaixaRoutes;
